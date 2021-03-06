package com.deniz.easify.ui.profile.playlists.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.source.remote.request.CreatePlaylistBody
import com.deniz.easify.data.source.repositories.PlaylistRepository
import com.deniz.easify.util.Event
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2020-01-28
 */

class CreatePlaylistViewModel(
    private val playlistRepository: PlaylistRepository
) : ViewModel() {

    private val _navigate = MutableLiveData<Event<Boolean>>()
    val navigate: LiveData<Event<Boolean>> = _navigate

    fun createPlaylist(name: String, description: String) {
        viewModelScope.launch {
            playlistRepository.createPlaylist(
                CreatePlaylistBody(name, description)
            )

            _navigate.value = Event(true)
        }
    }
}
