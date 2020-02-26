package com.deniz.easify.ui.search.features.discover.recommended

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result
import com.deniz.easify.data.source.remote.response.FeaturesObject
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.data.source.remote.utils.parseNetworkError
import com.deniz.easify.data.source.repositories.BrowseRepository
import com.deniz.easify.util.Event
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2020-01-02
 */

class RecommendedTracksViewModel(
    private val browseRepository: BrowseRepository
) : ViewModel() {

    private val _event = MutableLiveData<Event<RecommendedTracksViewEvent>>()
    val event: LiveData<Event<RecommendedTracksViewEvent>> = _event

    private val _loading = MutableLiveData<Boolean>(true)
    val loading: LiveData<Boolean> = _loading

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun sendEvent(event: RecommendedTracksViewEvent) {
        _event.value = Event(event)
    }

    fun start(features: FeaturesObject?) {
        features?.let {
            fetchRecommendations(features)
            return
        }

        sendEvent(RecommendedTracksViewEvent.ShowError(
            "Could not load the features you set from previous page." +
                "Please go back and try again."))
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
                        _loading.value = false
                        sendEvent(RecommendedTracksViewEvent.NotifyDataChanged(result.data.tracks))
                    }

                    is Result.Error -> {
                        _loading.value = false
                        sendEvent(RecommendedTracksViewEvent.ShowError(parseNetworkError(result.exception)))
                    }
                }
            }
        }
    }

    // Called by Data Binding.
    fun openTrackFragment(track: Track) {
        sendEvent(RecommendedTracksViewEvent.OpenTrackOnSpotify(track))
    }
}
