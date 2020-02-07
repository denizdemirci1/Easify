package com.deniz.easify.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deniz.easify.data.source.Repository
import com.deniz.easify.data.source.remote.response.User
import com.deniz.easify.util.AuthManager

/**
 * @User: deniz.demirci
 * @Date: 2019-11-19
 */

class ProfileViewModel(
    private val authManager: AuthManager,
    private val repository: Repository
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    init {
        _user.value = authManager.user!!
    }
}
