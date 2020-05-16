package com.deniz.easify.ui.search.features.discover.recommended

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result
import com.deniz.easify.data.source.remote.request.CreatePlaylistBody
import com.deniz.easify.data.source.remote.response.FeaturesObject
import com.deniz.easify.data.source.remote.response.Playlist
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.data.source.remote.utils.parseNetworkError
import com.deniz.easify.data.source.repositories.BrowseRepository
import com.deniz.easify.data.source.repositories.PlaylistRepository
import com.deniz.easify.util.AuthManager
import com.deniz.easify.util.Event
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2020-01-02
 */

class RecommendedTracksViewModel(
    private val authManager: AuthManager,
    private val browseRepository: BrowseRepository,
    private val playlistRepository: PlaylistRepository
) : ViewModel() {

    private val _event = MutableLiveData<Event<RecommendedTracksViewEvent>>()
    val event: LiveData<Event<RecommendedTracksViewEvent>> = _event

    private val _loading = MutableLiveData<Boolean>(true)
    val loading: LiveData<Boolean> = _loading

    private val _toolbarLoading = MutableLiveData<Boolean>(false)
    val toolbarLoading: LiveData<Boolean> = _toolbarLoading

    private val recommendedTracks = ArrayList<Track>()
    private var createdPlaylist : Playlist? = null
    private var createdPlaylistName: String? = null

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun sendEvent(event: RecommendedTracksViewEvent) {
        _event.value = Event(event)
    }

    fun start(features: FeaturesObject?) {
        setUserReadyToRate()
        features?.let {
            fetchRecommendations(features)
            return
        }

        sendEvent(RecommendedTracksViewEvent.ShowError(
            "Could not load the features you set from previous page." +
                "Please go back and try again."))
    }

    private fun setUserReadyToRate() {
        authManager.isReadyToRate = true
    }

    private fun fetchRecommendations(features: FeaturesObject) {
        viewModelScope.launch {
            browseRepository.fetchRecommendations(
                features.danceability / 100,
                features.energy / 100,
                features.speechiness / 100,
                features.acousticness / 100,
                features.instrumentalness / 100,
                features.liveness / 100,
                features.valence / 100,
                features.tempo,
                features.id
            ).let { result ->
                when (result) {
                    is Result.Success -> {
                        _loading.value = false
                        recommendedTracks.addAll(result.data.tracks)
                        sendEvent(RecommendedTracksViewEvent.SetTitle(recommendedTracks.size))
                        sendEvent(RecommendedTracksViewEvent.NotifyDataChanged(recommendedTracks))
                    }

                    is Result.Error -> {
                        _loading.value = false
                        sendEvent(RecommendedTracksViewEvent.ShowError(parseNetworkError(result.exception)))
                    }
                }
            }
        }
    }

    fun createPlaylist(name: String, description: String) {
        _toolbarLoading.value = true
        viewModelScope.launch {
            playlistRepository.createPlaylist(
                CreatePlaylistBody(name, description)
            )
            createdPlaylistName = name
            // fetch all playlists to find the created playlist's id. Need it for adding tracks.
            findPlaylist()
        }
    }

    private fun findPlaylist() {
        viewModelScope.launch {
            playlistRepository.fetchPlaylists().let { result ->
                when (result) {
                    is Result.Success -> {
                        createdPlaylist = result.data.playlists.find {
                            it.name == createdPlaylistName
                        }
                        addTracksToThePlaylist(createdPlaylist?.id)
                    }

                    is Result.Error -> {
                        _toolbarLoading.value = false
                        sendEvent(RecommendedTracksViewEvent.ShowSnackBar(false))
                    }
                }
            }
        }
    }

    private fun addTracksToThePlaylist(playlistId: String?) {
        playlistId?.let { id ->
            var uris = ""
            for (track in recommendedTracks) {
                uris += "${track.uri},"
            }
            viewModelScope.launch {
                playlistRepository.addTrackToPlaylist(id, uris)
                _toolbarLoading.value = false
                sendEvent(RecommendedTracksViewEvent.ShowSnackBar(true))
                sendEvent(RecommendedTracksViewEvent.OpenPlaylistsFragment)
            }
            return
        }

        sendEvent(RecommendedTracksViewEvent.ShowSnackBar(false))
    }

    // Called by Data Binding.
    fun openTrackFragment(track: Track) {
        sendEvent(RecommendedTracksViewEvent.OpenTrackOnSpotify(track))
    }
}
