package com.deniz.easify.ui.profile.playlists

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result
import com.deniz.easify.data.source.remote.response.Playlist
import com.deniz.easify.data.source.remote.response.PlaylistTracks
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.data.source.remote.utils.parseNetworkError
import com.deniz.easify.data.source.repositories.PlaylistRepository
import com.deniz.easify.util.AuthManager
import com.deniz.easify.util.Event
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2020-01-23
 */

class PlaylistViewModel(
    private val authManager: AuthManager,
    private val playlistRepository: PlaylistRepository
) : ViewModel() {

    /**
     * There are four sources to this fragment. SearchFragment, HistoryFragment, ProfileFragment
     *
     * @SearchFragment,
     * @HistoryFragment: Came with a track. Pick a playlist and [ADD] this track to it.
     *
     * @ProfileFragment,
     * @RecommendedTracksFragment: Came without a track. Pick a playlist and [SEE] details of
     * the playlist or click on the eye icon and view playlist on Spotify.
     */
    enum class Reason {
        SEE, ADD
    }

    private val _event = MutableLiveData<Event<PlaylistViewEvent>>()
    val event: LiveData<Event<PlaylistViewEvent>> = _event

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun sendEvent(event: PlaylistViewEvent) {
        _event.value = Event(event)
    }

    private val _loading = MutableLiveData<Boolean>(true)
    val loading: LiveData<Boolean> = _loading

    private val _showSeeIcon = MutableLiveData<Boolean>(true)
    val showSeeIcon: LiveData<Boolean> = _showSeeIcon

    private val playlistsToShow = ArrayList<Playlist>()
    private val playlistsTracksToShow = ArrayList<PlaylistTracks>()

    private lateinit var reason: Reason


    /**
     * [requestCount]: To determine offset value for fetchPlaylistTracks request
     */
    private var requestCount = 0

    /**
     * To keep ids of clicked playlists so if it is clicked before, that means the track is already
     * there. So we show snackbar to say "the track already exists" and return from the function
     */
    private var clickedPlaylistIds = ArrayList<String>()

    fun start(track: Track?) {
        track?.let {
            reason = Reason.ADD
            _showSeeIcon.value = false
            sendEvent(PlaylistViewEvent.InitUI(Reason.ADD))
            fetchPlaylists(true)
            return
        }
        reason = Reason.SEE
        _showSeeIcon.value = true
        sendEvent(PlaylistViewEvent.InitUI(Reason.SEE))
        fetchPlaylists(false)
    }

    private fun fetchPlaylists(onlyEditablePlaylists: Boolean) {
        viewModelScope.launch {
            playlistRepository.fetchPlaylists().let { result ->
                when (result) {
                    is Result.Success -> {
                        _loading.value = false
                        playlistsToShow.clear()
                        playlistsToShow.addAll(
                            if (onlyEditablePlaylists)
                                result.data.playlists.filter { it.owner.id == authManager.user!!.id }
                            else
                                result.data.playlists
                        )
                        sendEvent(PlaylistViewEvent.NotifyDataChanged(ArrayList(playlistsToShow)))
                    }
                    is Result.Error -> {
                        _loading.value = false
                        sendEvent(PlaylistViewEvent.ShowError(parseNetworkError(result.exception)))
                    }
                }
            }
        }
    }

    /**
     * Check if the track is already in the selected playlist.
     * if it is -> return
     * if it is not -> add track to the playlist
     */
    private fun addTrackToPlaylist(track: Track, playlist: Playlist) {
        viewModelScope.launch {
            clickedPlaylistIds.add(playlist.id)
            // if track already exist in the playlist, return
            for (playlistTrack in playlistsTracksToShow) {
                if (playlistTrack.track.id == track.id) {
                    sendEvent(PlaylistViewEvent.TrackAddingFailed(track.name, playlist.name))
                    return@launch
                }
            }
            // if track doesn't exist in the playlist, add
            playlistRepository.addTrackToPlaylist(playlist.id, track.uri)
            sendEvent(PlaylistViewEvent.TrackAddingSucceeded(track.name, playlist.name))
        }
    }

    fun fetchPlaylistTracks(track: Track, playlist: Playlist) {
        if (clickedPlaylistIds.contains(playlist.id)) {
            sendEvent(PlaylistViewEvent.TrackAddingFailed(track.name, playlist.name))
            return
        }

        viewModelScope.launch {
            playlistRepository.fetchPlaylistTracks(playlist.id, requestCount * 100).let { result ->
                when (result) {
                    is Result.Success -> {
                        requestCount ++
                        playlistsTracksToShow.addAll(result.data.playlistTracks)
                        if (playlistsTracksToShow.size < result.data.total)
                            fetchPlaylistTracks(track, playlist)
                        else
                            addTrackToPlaylist(track, playlist)
                    }
                    is Result.Error -> {
                        sendEvent(PlaylistViewEvent.ShowError(parseNetworkError(result.exception)))
                    }
                }
            }
        }
    }

    /**
     * Called by Data Binding.
     */
    fun playlistClicked(playlist: Playlist) {
        requestCount = 0
        when (reason) {
            Reason.SEE -> {
                sendEvent(
                    PlaylistViewEvent.OpenPlaylistDetail(
                        playlist,
                        playlist.owner.id == authManager.user!!.id
                    )
                )
            }

            Reason.ADD -> {
                sendEvent(PlaylistViewEvent.FetchPlaylistTracks(playlist))
            }
        }
    }

    fun openPlaylistOnSpotify(playlist: Playlist) {
        sendEvent(PlaylistViewEvent.OpenPlaylistOnSpotify(playlist))
    }
}
