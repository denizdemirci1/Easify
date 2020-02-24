package com.deniz.easify.ui.profile.playlists.detail

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result.Error
import com.deniz.easify.data.Result.Success
import com.deniz.easify.data.source.remote.utils.parseNetworkError
import com.deniz.easify.data.source.remote.request.RemoveTracksBody
import com.deniz.easify.data.source.remote.request.Uri
import com.deniz.easify.data.source.remote.response.Playlist
import com.deniz.easify.data.source.remote.response.PlaylistTracks
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.data.source.repositories.PlaylistRepository
import com.deniz.easify.util.Event
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2020-01-23
 */

class PlaylistDetailViewModel(
    private val playlistRepository: PlaylistRepository
) : ViewModel() {

    private val _event = MutableLiveData<Event<PlaylistDetailViewEvent>>()
    val event: LiveData<Event<PlaylistDetailViewEvent>> = _event

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun sendEvent(event: PlaylistDetailViewEvent) {
        _event.value = Event(event)
    }

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _editable = MutableLiveData<Boolean>()
    val editable: LiveData<Boolean> = _editable

    private val _showNoTracksLayout = MutableLiveData<Boolean>()
    val showNoTracksLayout: LiveData<Boolean> = _showNoTracksLayout

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val playlistsTracksToShow = ArrayList<PlaylistTracks>()
    private lateinit var playlistId: String

    private var requestCount = 0

    fun start(playlist: Playlist?, editable: Boolean) {
        _showNoTracksLayout.value = false

        playlist?.let {
            fetchPlaylistTracks(playlist.id).also { this.playlistId = playlist.id }
            _title.value = playlist.name
            _editable.value = editable

            return
        }
        Log.i("PlaylistDetailViewModel", "missing playlist object")
    }

    private fun fetchPlaylistTracks(playlistId: String) {
        viewModelScope.launch {
            _loading.value = true
            playlistRepository.fetchPlaylistTracks(playlistId, requestCount * 100).let { result ->
                when (result) {
                    is Success -> {
                        requestCount ++
                        playlistsTracksToShow.addAll(result.data.playlistTracks)
                        if (playlistsTracksToShow.size < result.data.total)
                            fetchPlaylistTracks(playlistId)
                        else {
                            sendEvent(PlaylistDetailViewEvent.NotifyDataChanged(
                                    ArrayList(playlistsTracksToShow))
                            )
                            _loading.value = false
                            if (playlistsTracksToShow.isEmpty())
                                _showNoTracksLayout.value = true
                        }
                    }
                    is Error -> {
                        sendEvent(PlaylistDetailViewEvent.ShowError(
                            parseNetworkError(result.exception))
                        )
                        _loading.value = false
                    }
                }
            }
        }
    }

    private fun removeTrackFromPlaylist(track: Track) {
        viewModelScope.launch {
            val uri = listOf(Uri(track.uri))
            playlistRepository.removeTrackFromPlaylist(playlistId, RemoveTracksBody(uri))
            sendEvent(PlaylistDetailViewEvent.ShowSnackBar(track.name))
            sendEvent(PlaylistDetailViewEvent.NotifyDataChanged(ArrayList(playlistsTracksToShow)))
        }
    }

    fun openTrack(track: Track) {
        sendEvent(PlaylistDetailViewEvent.OpenFeatureFragment(track))
    }

    fun removeTrack(track: Track) {
        playlistsTracksToShow.removeAll { it.track.id == track.id}
        removeTrackFromPlaylist(track)
    }
}
