package com.deniz.easify.ui.profile.favorites.topArtists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deniz.easify.data.source.SpotifyRepository
import com.deniz.easify.data.source.remote.response.TopArtist

/**
 * @User: deniz.demirci
 * @Date: 2019-12-02
 */

class TopArtistsViewModel(
    private val repository: SpotifyRepository
) : ViewModel() {

    private val _topArtist = MutableLiveData<TopArtist>()
    val topArtist: LiveData<TopArtist> = _topArtist

    fun start(artists: TopArtist?) {

        if (artists != null) {
            _topArtist.value = artists
        }
    }
}
