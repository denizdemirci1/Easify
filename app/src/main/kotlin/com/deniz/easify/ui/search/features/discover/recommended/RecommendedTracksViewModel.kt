package com.deniz.easify.ui.search.features.discover.recommended

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deniz.easify.data.source.remote.response.RecommendationsObject
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.util.Event

/**
 * @User: deniz.demirci
 * @Date: 2020-01-02
 */

class RecommendedTracksViewModel : ViewModel() {

    private val _recommendedTracks = MutableLiveData<ArrayList<Track>>().apply { value = arrayListOf() }
    val recommendedTracks: LiveData<ArrayList<Track>> = _recommendedTracks

    private val _openTrackFragmentEvent = MutableLiveData<Event<Track>>()
    val openTrackFragmentEvent: LiveData<Event<Track>> = _openTrackFragmentEvent

    fun start(recommendations: RecommendationsObject?) {
        recommendations?.let {
            _recommendedTracks.value = it.tracks
        }
    }

    // Called by Data Binding.
    fun openTrackFragment(track: Track) {
        _openTrackFragmentEvent.value = Event(track)
    }
}
