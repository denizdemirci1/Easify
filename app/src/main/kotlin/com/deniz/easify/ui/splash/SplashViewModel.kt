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

open class SplashViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _event = MutableLiveData<Event<SplashViewEvent>>()
    val event: LiveData<Event<SplashViewEvent>> = _event

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun sendEvent(event: SplashViewEvent) {
        _event.value = Event(event)
    }

    fun saveToken(accessToken: String) {
        userRepository.saveToken(accessToken)
    }

    fun clearToken() {
        userRepository.clearToken()
    }

    fun fetchUser() {
        viewModelScope.launch {
            userRepository.fetchUser().let { result ->
                when (result) {
                    is Success -> {
                        userRepository.saveUser(result.data)
                        userRepository.setTokenRefreshed(false)
                        sendEvent(SplashViewEvent.OpenSearchFragment)
                    }
                    is Error -> {
                        userRepository.saveToken(null)
                        handleAuthError(parseNetworkError(result.exception))
                    }
                }
            }
        }
    }

    open fun authenticateSpotify() {
        if (!userRepository.getToken().isNullOrEmpty()) {
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
        if (userRepository.getTokenRefreshed()) {
            sendEvent(SplashViewEvent.ShowError(message))
            userRepository.setTokenRefreshed(false)
        } else {
            userRepository.setTokenRefreshed(true)
            authenticateSpotify()
        }
    }
}
