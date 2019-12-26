package com.deniz.easify.ui.profile.followings.follow.artist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.source.SpotifyRepository
import com.deniz.easify.data.source.remote.response.Artist
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2019-12-25
 */

class ArtistViewModel(
    private val repository: SpotifyRepository
) : ViewModel() {

    enum class Follow {
        FOLLOW, UNFOLLOW
    }

    private val _artist = MutableLiveData<Artist>()
    val artist: LiveData<Artist> = _artist

    private val _showSnackbarMessage = MutableLiveData<Follow>()
    val showSnackbarMessage: LiveData<Follow> = _showSnackbarMessage

    fun start(artist: Artist?) {

        if (artist != null) {
            _artist.value = artist
        }
    }

    fun followArtist(id: String) {
        viewModelScope.launch {
            repository.followArtist(id = id)
            _showSnackbarMessage.value = Follow.FOLLOW
        }
    }

    fun unfollowArtist(id: String) {
        viewModelScope.launch {
            repository.unfollowArtist(id = id)
            _showSnackbarMessage.value = Follow.UNFOLLOW
        }
    }

}