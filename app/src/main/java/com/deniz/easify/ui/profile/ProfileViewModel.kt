package com.deniz.easify.ui.profile

import androidx.lifecycle.ViewModel
import com.deniz.easify.data.source.SpotifyRepository
import com.deniz.easify.util.AuthManager
import androidx.lifecycle.MutableLiveData

/**
 * @User: deniz.demirci
 * @Date: 2019-11-19
 */

class ProfileViewModel(
    private val authManager: AuthManager,
    private val repository: SpotifyRepository
) : ViewModel() {

    val profilePictureUrl = MutableLiveData<String>()

    init {
        profilePictureUrl.value = authManager.user!!.images[0].url
    }


}
