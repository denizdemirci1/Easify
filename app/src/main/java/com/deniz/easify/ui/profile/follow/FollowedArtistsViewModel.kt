package com.deniz.easify.ui.profile.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result
import com.deniz.easify.data.source.SpotifyRepository
import com.deniz.easify.data.source.remote.response.Artist
import com.deniz.easify.data.source.remote.response.FollowedArtistsResponse
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2019-12-25
 */

class FollowedArtistsViewModel (
    private val repository: SpotifyRepository
): ViewModel() {

    private val _artists = MutableLiveData<ArrayList<Artist>>().apply { value = arrayListOf() }
    val artists: LiveData<ArrayList<Artist>> = _artists

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun fetchFollowedArtists() {
        viewModelScope.launch {
            repository.fetchFollowedArtists().let { result ->
                if (result is Result.Success) {
                    _artists.value = result.data.artists.items
                } else {
                    _errorMessage.value = "fetch followed artists request'i patladı"
                }
            }
        }
    }
}