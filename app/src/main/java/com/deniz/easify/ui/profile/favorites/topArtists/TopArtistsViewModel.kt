package com.deniz.easify.ui.profile.favorites.topArtists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deniz.easify.data.source.remote.response.Artist
import com.deniz.easify.data.source.remote.response.TopArtist
import com.deniz.easify.util.Event

/**
 * @User: deniz.demirci
 * @Date: 2019-12-02
 */

class TopArtistsViewModel : ViewModel() {

    private val _openArtistFragmentEvent = MutableLiveData<Event<Artist>>()
    val openArtistFragmentEvent: LiveData<Event<Artist>> = _openArtistFragmentEvent

    private val _topArtist = MutableLiveData<TopArtist>()
    val topArtist: LiveData<TopArtist> = _topArtist

    fun start(artists: TopArtist?) {

        if (artists != null) {
            _topArtist.value = artists
        }
    }

    // Called by Data Binding.
    fun openArtistFragment(artist: Artist) {
        _openArtistFragmentEvent.value = Event(artist)
    }
}
