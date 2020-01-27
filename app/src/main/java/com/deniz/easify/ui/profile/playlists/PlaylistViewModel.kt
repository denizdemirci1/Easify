package com.deniz.easify.ui.profile.playlists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result
import com.deniz.easify.data.source.Repository
import com.deniz.easify.data.source.remote.parseNetworkError
import com.deniz.easify.data.source.remote.response.Playlist
import com.deniz.easify.data.source.remote.response.PlaylistTracks
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

    /**
     * The form of _trackAddingResult is a pair that holds a pair and a boolean
     * The inner pair holds trackName and playlistName
     *
     * Pair <Pair(trackName, playlistName), isSuccessfullyAdded>
     */
    private val _trackAddingResult = MutableLiveData<Event<Pair<Pair<String, String>, Boolean>>>()
    val trackAddingResult: LiveData<Event<Pair<Pair<String, String>, Boolean>>> = _trackAddingResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _reason = MutableLiveData<Reason>()
    val reason: LiveData<Reason> = _reason

    private val playlistsToShow = ArrayList<Playlist>()

    private val playlistsTracksToShow = ArrayList<PlaylistTracks>()

    /**
     * @param requestCount: To determine offset value for fetchPlaylistTracks request
     * @param deletedTrackCount: To calculate fetched track count with total track count in playlist
     */
    private var requestCount = 0
    private var deletedTrackCount = 0

    /**
     * To keep ids of clicked playlists so if its clicked before, that means the track is already
     * there. So we show snackbar to say "the track already exists" and return from the function
     */
    private var clickedPlaylistIds = ArrayList<String>()

    fun start(track: Track?) {
        track?.let {
            _reason.value = Reason.ADD
            fetchPlaylists(true)
            return
        }
        _reason.value = Reason.SEE
        fetchPlaylists(false)
    }

    private fun fetchPlaylists(onlyEditablePlaylists: Boolean) {
        viewModelScope.launch {
            repository.fetchPlaylists(authManager.user!!.id).let { result ->
                when (result) {
                    is Result.Success -> {
                        playlistsToShow.clear()
                        playlistsToShow.addAll(
                            if (onlyEditablePlaylists)
                                result.data.playlists.filter { it.owner.id == authManager.user!!.id }
                            else
                                result.data.playlists
                        )
                        _playlists.value = ArrayList(playlistsToShow)
                    }
                    is Result.Error -> _errorMessage.value = parseNetworkError(result.exception)
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
            for (playlistTrack in playlistsTracksToShow){
                if (playlistTrack.track.id == track.id) {
                    _trackAddingResult.value = Event(Pair(Pair(track.name, playlist.name), false))
                    return@launch
                }
            }
            // if track doesn't exist in the playlist, add
            repository.addTrackToPlaylist(playlist.id, track.uri)
            _trackAddingResult.value = Event(Pair(Pair(track.name, playlist.name), true))
        }
    }

    fun fetchPlaylistTracks(track: Track, playlist: Playlist) {
        if (clickedPlaylistIds.contains(playlist.id)) {
            _trackAddingResult.value = Event(Pair(Pair(track.name, playlist.name), false))
            return
        }

        viewModelScope.launch {
            repository.fetchPlaylistTracks(playlist.id, requestCount * 100).let { result ->
                when (result) {
                    is Result.Success -> {
                        requestCount ++
                        playlistsTracksToShow.addAll(
                            result.data.playlistTracks
                                .filter { playlistTracks ->
                                    playlistTracks.track.album.images.size > 0 .also {
                                        if (playlistTracks.track.album.images.isNullOrEmpty())
                                            deletedTrackCount ++
                                    }
                                }
                        )
                        if (playlistsTracksToShow.size + deletedTrackCount < result.data.total)
                            fetchPlaylistTracks(track, playlist)
                        else
                            addTrackToPlaylist(track, playlist)
                    }
                    is Result.Error -> _errorMessage.value = parseNetworkError(result.exception)
                }
            }
        }
    }

    /**
     * Called by Data Binding.
     */
    fun playlistClicked(playlist: Playlist) {
        _playlistClickedEvent.value = Event(Pair(playlist, playlist.owner.id == authManager.user!!.id))
    }
}
