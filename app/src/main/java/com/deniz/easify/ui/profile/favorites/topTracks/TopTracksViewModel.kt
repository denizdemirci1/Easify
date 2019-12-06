package com.deniz.easify.ui.profile.favorites.topTracks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deniz.easify.data.source.SpotifyRepository
import com.deniz.easify.data.source.remote.response.TopTrack

/**
 * @User: deniz.demirci
 * @Date: 2019-12-04
 */

class TopTracksViewModel(
    private val repository: SpotifyRepository
) : ViewModel() {

    private val _topTrack = MutableLiveData<TopTrack>()
    val topTrack: LiveData<TopTrack> = _topTrack

    fun start(track: TopTrack?) {

        if (track != null) {
            _topTrack.value = track
        }
    }
}
