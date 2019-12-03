package com.deniz.easify.ui.profile.favorites.favoriteDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deniz.easify.data.source.SpotifyRepository
import com.deniz.easify.data.source.remote.response.Track

/**
 * @User: deniz.demirci
 * @Date: 2019-12-02
 */

class FavoriteDetailsViewModel(
    private val repository: SpotifyRepository
) : ViewModel() {

    private val _track = MutableLiveData<Track>()
    val track: LiveData<Track> = _track

    fun start(track: Track?) {

        if (track != null) {
            _track.value = track
        }
    }

}