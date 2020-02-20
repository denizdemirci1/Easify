package com.deniz.easify.ui.search.features.discover.recommended

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result
import com.deniz.easify.data.source.remote.utils.parseNetworkError
import com.deniz.easify.data.source.remote.response.FeaturesObject
import com.deniz.easify.data.source.remote.response.RecommendationsObject
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.data.source.repositories.BrowseRepository
import com.deniz.easify.data.source.repositories.PlayerRepository
import com.deniz.easify.util.Event
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2020-01-02
 */

class RecommendedTracksViewModel(
    private val browseRepository: BrowseRepository
) : ViewModel() {

    private val _openTrackFragmentEvent = MutableLiveData<Event<Track>>()
    val openTrackFragmentEvent: LiveData<Event<Track>> = _openTrackFragmentEvent

    private val _recommendedTracks = MutableLiveData<RecommendationsObject>()
    val recommendedTracks: LiveData<RecommendationsObject> = _recommendedTracks

    private val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>> = _errorMessage

    fun start(features: FeaturesObject?) {
        features?.let {
            fetchRecommendations(features)
            return
        }

        _errorMessage.value = Event("Could not load the features you set from previous page." +
                "Please go back and try again.")
    }

    private fun fetchRecommendations(features: FeaturesObject) {
        viewModelScope.launch {
            browseRepository.fetchRecommendations(
                features.danceability,
                features.energy,
                features.speechiness,
                features.acousticness,
                features.instrumentalness,
                features.liveness,
                features.valence,
                features.tempo,
                features.id
            ).let { result ->
                when (result) {
                    is Result.Success -> {
                        _recommendedTracks.value = result.data
                    }
                    is Result.Error -> _errorMessage.value = Event(
                        parseNetworkError(
                            result.exception
                        )
                    )
                }
            }
        }
    }

    // Called by Data Binding.
    fun openTrackFragment(track: Track) {
        _openTrackFragmentEvent.value = Event(track)
    }
}
