package com.deniz.easify.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deniz.easify.data.source.remote.response.User
import com.deniz.easify.data.source.repositories.UserRepository

/**
 * @User: deniz.demirci
 * @Date: 2019-11-19
 */

class ProfileViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun fetchUser() {
        _user.value = userRepository.getUser()
    }
}
