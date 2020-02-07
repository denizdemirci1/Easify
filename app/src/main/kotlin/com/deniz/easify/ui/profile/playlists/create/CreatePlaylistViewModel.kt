package com.deniz.easify.ui.profile.playlists.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.source.Repository
import com.deniz.easify.data.source.remote.request.CreatePlaylistBody
import com.deniz.easify.util.AuthManager
import com.deniz.easify.util.Event
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2020-01-28
 */

class CreatePlaylistViewModel(
    private val authManager: AuthManager,
    private val repository: Repository
) : ViewModel() {

    private val _navigate = MutableLiveData<Event<Boolean>>()
    val navigate: LiveData<Event<Boolean>> = _navigate

    fun createPlaylist(name: String, description: String) {
        viewModelScope.launch {
            repository.createPlaylist(
                CreatePlaylistBody(name, description),
                authManager.user!!.id
            )

            _navigate.value = Event(true)
        }
    }
}
