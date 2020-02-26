package com.deniz.easify.ui.splash

/**
 * @User: deniz.demirci
 * @Date: 2020-02-20
 */

sealed class SplashViewEvent {

    object Authenticate : SplashViewEvent()

    object OpenSearchFragment : SplashViewEvent()

    data class ShowError(val message: String) : SplashViewEvent()
}
