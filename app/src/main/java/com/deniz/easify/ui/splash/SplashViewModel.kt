package com.deniz.easify.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result.Error
import com.deniz.easify.data.Result.Success
import com.deniz.easify.data.source.SpotifyRepository
import com.deniz.easify.data.source.remote.parseNetworkError
import com.deniz.easify.util.AuthManager
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2019-11-11
 */

class SplashViewModel(
    private val authManager: AuthManager,
    private val repository: SpotifyRepository
) : ViewModel() {

    companion object {
        private const val SPOTIFY_CLIENT_ID = "c7488b4d223740f195ed82e6ee3c3802"
        private const val SPOTIFY_URI_CALLBACK = "com.deniz.easify://callback"
        // TODO: Edit Scopes
        private const val SCOPES = "user-read-recently-played," +
                "user-library-modify," +
                "user-read-email," +
                "user-read-private," +
                "user-top-read"
    }

    private val _authenticationRequest = MutableLiveData<AuthenticationRequest>()
    val authenticationRequest: LiveData<AuthenticationRequest> = _authenticationRequest

    private val _navigateToMain = MutableLiveData<Boolean>()
    val navigateToMain: LiveData<Boolean> = _navigateToMain

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun saveToken(accessToken: String) {
        viewModelScope.launch {
            authManager.token = accessToken
        }
    }

    fun clearToken() {
        viewModelScope.launch {
            authManager.token = ""
        }
    }

    fun fetchUser() {
        viewModelScope.launch {
            repository.fetchUser().let { result ->
                if (result is Success) {
                    authManager.user = result.data
                    _navigateToMain.value = true
                } else if (result is Error) {
                    authManager.token = null
                    handleAuthError(parseNetworkError(result.exception))
                }
            }
        }
    }

    fun authenticateSpotify() {
        if (!authManager.token.isNullOrEmpty()) {
            fetchUser()
            return
        }

        val builder = AuthenticationRequest.Builder(
            SPOTIFY_CLIENT_ID,
            AuthenticationResponse.Type.TOKEN,
            SPOTIFY_URI_CALLBACK
        )

        builder.setScopes(arrayOf(SCOPES))
        _authenticationRequest.value = builder.build()
    }

    /***
     * If the auto authentication failed, try to refresh token by authenticating again
     * before showing error message.
     * If it fails again, then show the error.
     */
    fun handleAuthError(message: String) {
        if (authManager.tokenRefreshed) {
            _errorMessage.value = message
            authManager.tokenRefreshed = false
        } else {
            authManager.tokenRefreshed = true
            authenticateSpotify()
        }
    }
}
