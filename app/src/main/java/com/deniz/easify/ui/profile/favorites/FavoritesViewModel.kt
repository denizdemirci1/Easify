package com.deniz.easify.ui.profile.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result.Success
import com.deniz.easify.data.source.SpotifyRepository
import com.deniz.easify.data.source.remote.response.TopArtist
import com.deniz.easify.data.source.remote.response.TopTrack
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2019-12-02
 */

class FavoritesViewModel(
    private val repository: SpotifyRepository
) : ViewModel() {

    private val _topArtist = MutableLiveData<TopArtist>()
    val topArtist: LiveData<TopArtist> = _topArtist

    private val _topTracks = MutableLiveData<TopTrack>()
    val topTracks: LiveData<TopTrack> = _topTracks

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun fetchTopArtists(type: String, timeRange: String?, limit: Int?) {
        viewModelScope.launch {
            repository.fetchTopArtists(type, timeRange, limit).let { result ->
                if (result is Success) {
                    _topArtist.value = result.data
                } else {
                    _errorMessage.value = "fetch top artists request'i patladı"
                }
            }
        }
    }

    fun fetchTopTracks(type: String, timeRange: String?, limit: Int?) {
        viewModelScope.launch {
            repository.fetchTopTracks(type, timeRange, limit).let { result ->
                if (result is Success) {
                    _topTracks.value = result.data
                } else {
                    _errorMessage.value = "fetch top tracks request'i patladı"
                }
            }
        }
    }
}
