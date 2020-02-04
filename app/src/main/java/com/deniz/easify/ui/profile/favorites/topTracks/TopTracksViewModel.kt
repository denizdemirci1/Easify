package com.deniz.easify.ui.profile.favorites.topTracks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deniz.easify.data.source.remote.response.TopTrack
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.util.Event

/**
 * @User: deniz.demirci
 * @Date: 2019-12-04
 */

class TopTracksViewModel : ViewModel() {

    private val _topTrack = MutableLiveData<TopTrack>()
    val topTrack: LiveData<TopTrack> = _topTrack

    private val _openTrackEvent = MutableLiveData<Event<Track>>()
    val openTrackEvent: LiveData<Event<Track>> = _openTrackEvent

    private val _openPlaylistsPageEvent = MutableLiveData<Event<Track>>()
    val openPlaylistsPageEvent: LiveData<Event<Track>> = _openPlaylistsPageEvent

    fun start(track: TopTrack?) {

        if (track != null) {
            _topTrack.value = track
        }
    }

    /**
     * Called by Data Binding.
     */
    fun openTrack(track: Track) {
        _openTrackEvent.value = Event(track)
    }

    fun openPlaylistsPage(track: Track) {
        _openPlaylistsPageEvent.value = Event(track)
    }
}
