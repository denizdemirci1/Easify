package com.deniz.easify.ui.profile.playlists.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result.Error
import com.deniz.easify.data.Result.Success
import com.deniz.easify.data.source.Repository
import com.deniz.easify.data.source.remote.parseNetworkError
import com.deniz.easify.data.source.remote.request.RemoveTracksBody
import com.deniz.easify.data.source.remote.request.Uri
import com.deniz.easify.data.source.remote.response.Playlist
import com.deniz.easify.data.source.remote.response.PlaylistTracks
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.util.Event
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2020-01-23
 */

class PlaylistDetailViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _tracks = MutableLiveData<ArrayList<PlaylistTracks>>().apply { value = arrayListOf() }
    val tracks: LiveData<ArrayList<PlaylistTracks>> = _tracks

    private val _openTrackEvent = MutableLiveData<Event<Track>>()
    val openTrackEvent: LiveData<Event<Track>> = _openTrackEvent

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _editable = MutableLiveData<Boolean>()
    val editable: LiveData<Boolean> = _editable

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _showSnackbarMessage = MutableLiveData<Event<String>>()
    val showSnackbarMessage: LiveData<Event<String>> = _showSnackbarMessage

    private val playlistsTracksToShow = ArrayList<PlaylistTracks>()
    private lateinit var playlistId: String

    fun start(playlist: Playlist?, editable: Boolean) {
        playlist?.let {
            fetchPlaylistTracks(playlist.id).also { this.playlistId = playlist.id }
            _title.value = playlist.name
            _editable.value = editable

            return
        }
        Log.i("PlaylistDetailViewModel", "missing playlist id")
    }

    private fun fetchPlaylistTracks(playlistId: String) {
        viewModelScope.launch {
            repository.fetchPlaylistTracks(playlistId).let { result ->
                when (result) {
                    is Success -> {
                        playlistsTracksToShow.clear()
                        playlistsTracksToShow.addAll(result.data.playlistTracks.filter { it.track.album.images.size > 0 })
                        _tracks.value = ArrayList(playlistsTracksToShow)
                    }
                    is Error -> _errorMessage.value = parseNetworkError(result.exception)
                }
            }
        }
    }

    private fun removeTrackFromPlaylist(track: Track) {
        viewModelScope.launch {
            val uri = listOf(Uri(track.uri))
            repository.removeTrackFromPlaylist(playlistId, RemoveTracksBody(uri))
            _showSnackbarMessage.value = Event(track.name)
            fetchPlaylistTracks(playlistId)
        }
    }

    fun openTrack(track: Track) {
        _openTrackEvent.value = Event(track)
    }

    fun removeTrack(track: Track) {
        removeTrackFromPlaylist(track)
    }
}
