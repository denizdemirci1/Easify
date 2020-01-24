package com.deniz.easify.ui.profile.playlists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result
import com.deniz.easify.data.source.Repository
import com.deniz.easify.data.source.remote.parseNetworkError
import com.deniz.easify.data.source.remote.response.Playlist
import com.deniz.easify.data.source.remote.response.Track
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

    /**
     * There are three sources to this fragment. SearchFragment, HistoryFragment, ProfileFragment
     * @SearchFragment: Came with a track. Pick a playlist and ADD this track to it.
     * @HistoryFragment: Came with a track. Pick a playlist and ADD this track to it.
     * @ProfileFragment: Came without a track. Pick a playlist and SEE detail of the playlist.
     */
    enum class Reason {
        SEE, ADD
    }

    private val _playlists = MutableLiveData<ArrayList<Playlist>>().apply { value = arrayListOf() }
    val playlists: LiveData<ArrayList<Playlist>> = _playlists

    private val _playlistClickedEvent = MutableLiveData<Event<Pair<Playlist, Boolean>>>()
    val playlistClickedEvent: LiveData<Event<Pair<Playlist, Boolean>>> = _playlistClickedEvent

    private val _showSnackbarMessage = MutableLiveData<Event<Pair<String, String>>>()
    val showSnackbarMessage: LiveData<Event<Pair<String, String>>> = _showSnackbarMessage

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _reason = MutableLiveData<Reason>()
    val reason: LiveData<Reason> = _reason

    private val playlistsToShow = ArrayList<Playlist>()

    fun start(track: Track?) {
        track?.let {
            _reason.value = Reason.ADD
            return
        }
        _reason.value = Reason.SEE
    }

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

    // TODO: what happens if track is already in playlist. check!
    fun addTrackToPlaylist(track: Track, playlist: Playlist) {
        viewModelScope.launch {
            repository.addTrackToPlaylist(playlist.id, track.uri)
            _showSnackbarMessage.value = Event(Pair(playlist.name, track.name))
        }
    }

    /**
     * Called by Data Binding.
     */
    fun playlistClicked(playlist: Playlist) {
        _playlistClickedEvent.value = Event(Pair(playlist, playlist.owner.id == authManager.user!!.id))
    }
}
