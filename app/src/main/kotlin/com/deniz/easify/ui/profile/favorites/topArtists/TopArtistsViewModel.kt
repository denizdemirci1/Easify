package com.deniz.easify.ui.profile.favorites.topArtists

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deniz.easify.data.source.remote.response.Artist
import com.deniz.easify.data.source.remote.response.TopArtist
import com.deniz.easify.ui.history.HistoryViewEvent
import com.deniz.easify.util.Event

/**
 * @User: deniz.demirci
 * @Date: 2019-12-02
 */

class TopArtistsViewModel : ViewModel() {

    private val _event = MutableLiveData<Event<TopArtistsViewEvent>>()
    val event: LiveData<Event<TopArtistsViewEvent>> = _event

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun sendEvent(event: TopArtistsViewEvent) {
        _event.value = Event(event)
    }

    fun start(artists: TopArtist?) {
        artists?.let {
            sendEvent(TopArtistsViewEvent.NotifyDataChanged(it))
        }
    }

    // Called by Data Binding.
    fun openArtistFragment(artist: Artist) {
        sendEvent(TopArtistsViewEvent.OpenArtistFragment(artist))
    }
}
