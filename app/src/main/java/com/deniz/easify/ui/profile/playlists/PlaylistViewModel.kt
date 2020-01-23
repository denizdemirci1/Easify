package com.deniz.easify.ui.profile.playlists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result
import com.deniz.easify.data.source.Repository
import com.deniz.easify.data.source.remote.parseNetworkError
import com.deniz.easify.data.source.remote.response.Playlist
import com.deniz.easify.util.AuthManager
import com.deniz.easify.util.Event
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2020-01-23
 */

class PlaylistViewModel(
    private val authManager: AuthManager,
    private val repository: Repository
) : ViewModel() {

    private val _playlists = MutableLiveData<ArrayList<Playlist>>().apply { value = arrayListOf() }
    val playlists: LiveData<ArrayList<Playlist>> = _playlists

    private val _openPlaylistEvent = MutableLiveData<Event<Pair<Playlist, Boolean>>>()
    val openPlaylistEvent: LiveData<Event<Pair<Playlist, Boolean>>> = _openPlaylistEvent

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val playlistsToShow = ArrayList<Playlist>()

    fun fetchPlaylists() {
        viewModelScope.launch {
            repository.fetchPlaylists(authManager.user!!.id).let { result ->
                when (result) {
                    is Result.Success -> {
                        playlistsToShow.clear()
                        playlistsToShow.addAll(result.data.playlists)
                        _playlists.value = ArrayList(playlistsToShow)
                    }
                    is Result.Error -> _errorMessage.value = parseNetworkError(result.exception)
                }
            }
        }
    }

    /**
     * Called by Data Binding.
     */
    fun openPlaylist(playlist: Playlist) {
        _openPlaylistEvent.value = Event(Pair(playlist, playlist.owner.id == authManager.user!!.id))
    }
}
