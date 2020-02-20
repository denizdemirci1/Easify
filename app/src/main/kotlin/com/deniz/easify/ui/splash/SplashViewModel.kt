package com.deniz.easify.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result.Error
import com.deniz.easify.data.Result.Success
import com.deniz.easify.data.source.remote.utils.parseNetworkError
import com.deniz.easify.data.source.repositories.UserRepository
import com.deniz.easify.util.AuthManager
import com.deniz.easify.util.Event
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2019-11-11
 */

class SplashViewModel(
    private val authManager: AuthManager,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _authenticate = MutableLiveData<Boolean>()
    val authenticate: LiveData<Boolean> = _authenticate

    private val _navigateToMain = MutableLiveData<Event<Boolean>>()
    val navigateToMain: LiveData<Event<Boolean>> = _navigateToMain

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun saveToken(accessToken: String) {
        authManager.token = accessToken
    }

    fun clearToken() {
        authManager.token = ""
    }

    fun fetchUser() {
        viewModelScope.launch {
            userRepository.fetchUser().let { result ->
                when (result) {
                    is Success -> {
                        authManager.user = result.data
                        authManager.tokenRefreshed = false
                        _navigateToMain.value = Event(true)
                    }
                    is Error -> {
                        authManager.token = null
                        handleAuthError(
                            parseNetworkError(
                                result.exception
                            )
                        )
                    }
                }
            }
        }
    }

    fun authenticateSpotify() {
        if (!authManager.token.isNullOrEmpty()) {
            fetchUser()
            return
        }

        _authenticate.value = true
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
