package com.deniz.easify.ui.profile.followings.artist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result.Error
import com.deniz.easify.data.Result.Success
import com.deniz.easify.data.source.remote.response.Artist
import com.deniz.easify.data.source.remote.utils.parseNetworkError
import com.deniz.easify.data.source.repositories.FollowRepository
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2019-12-25
 */

class ArtistViewModel(
    private val followRepository: FollowRepository
) : ViewModel() {

    enum class Follow {
        FOLLOW, UNFOLLOW
    }

    private val _artists = MutableLiveData<ArrayList<Artist>>().apply { value = arrayListOf() }
    val artists: LiveData<ArrayList<Artist>> = _artists

    private val _artist = MutableLiveData<Artist>()
    val artist: LiveData<Artist> = _artist

    private val _showFollowButton = MutableLiveData<Boolean>()
    val showFollowButton: LiveData<Boolean> = _showFollowButton

    private val _showSnackbarMessage = MutableLiveData<Follow>()
    val showSnackbarMessage: LiveData<Follow> = _showSnackbarMessage

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun start(artist: Artist?) {

        if (artist != null) {
            _artist.value = artist
        }
    }

    fun fetchFollowedArtists() {
        viewModelScope.launch {
            followRepository.fetchFollowedArtists().let { result ->
                when (result) {
                    is Success -> {
                        _artists.value = result.data.artists.items
                        setButtonVisibilities(result.data.artists.items)
                    }
                    is Error -> _errorMessage.value =
                        parseNetworkError(
                            result.exception
                        )
                }
            }
        }
    }

    fun followArtist(id: String) {
        viewModelScope.launch {
            followRepository.followArtist(id = id)
            _showSnackbarMessage.value = Follow.FOLLOW
        }
    }

    fun unfollowArtist(id: String) {
        viewModelScope.launch {
            followRepository.unfollowArtist(id = id)
            _showSnackbarMessage.value = Follow.UNFOLLOW
        }
    }

    private fun setButtonVisibilities(artists: ArrayList<Artist>) {
        var isArtistFollowed = false
        for (artist in artists) {
            if (artist.id == _artist.value?.id) {
                isArtistFollowed = true
                break
            }
        }
        _showFollowButton.value = !isArtistFollowed
    }
}
