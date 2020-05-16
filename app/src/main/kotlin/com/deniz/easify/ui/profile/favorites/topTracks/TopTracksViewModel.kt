package com.deniz.easify.ui.profile.favorites.topTracks

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deniz.easify.data.source.remote.response.TopTrack
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.util.AuthManager
import com.deniz.easify.util.Event

/**
 * @User: deniz.demirci
 * @Date: 2019-12-04
 */

class TopTracksViewModel(
    private val authManager: AuthManager
) : ViewModel() {

    private val _event = MutableLiveData<Event<TopTracksViewEvent>>()
    val event: LiveData<Event<TopTracksViewEvent>> = _event

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun sendEvent(event: TopTracksViewEvent) {
        _event.value = Event(event)
    }

    fun start(track: TopTrack?) {
        setUserReadyToRate()
        track?.let {
            sendEvent(TopTracksViewEvent.NotifyDataChanged(it))
        }
    }

    private fun setUserReadyToRate() {
        authManager.isReadyToRate = true
    }

    /**
     * Called by Data Binding.
     */
    fun openTrack(track: Track) {
        sendEvent(TopTracksViewEvent.OpenFeaturesFragment(track))
    }

    fun openPlaylistsPage(track: Track) {
        sendEvent(TopTracksViewEvent.OpenPlaylistsFragment(track))
    }
}
