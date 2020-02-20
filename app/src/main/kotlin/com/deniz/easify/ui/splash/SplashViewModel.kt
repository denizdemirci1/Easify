package com.deniz.easify.ui.splash

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result.Error
import com.deniz.easify.data.Result.Success
import com.deniz.easify.data.source.remote.utils.parseNetworkError
import com.deniz.easify.data.source.repositories.UserRepository
import com.deniz.easify.ui.search.SearchViewEvent
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

    private val _event = MutableLiveData<Event<SplashViewEvent>>()
    val event: LiveData<Event<SplashViewEvent>> = _event

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun sendEvent(event: SplashViewEvent) {
        _event.value = Event(event)
    }

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
                        sendEvent(SplashViewEvent.OpenSearchFragment)
                    }
                    is Error -> {
                        authManager.token = null
                        handleAuthError(parseNetworkError(result.exception))
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

        sendEvent(SplashViewEvent.Authenticate)
    }

    /***
     * If the auto authentication failed, try to refresh token by authenticating again
     * before showing error message.
     * If it fails again, then show the error.
     */
    fun handleAuthError(message: String) {
        if (authManager.tokenRefreshed) {
            sendEvent(SplashViewEvent.ShowError(message))
            authManager.tokenRefreshed = false
        } else {
            authManager.tokenRefreshed = true
            authenticateSpotify()
        }
    }
}
