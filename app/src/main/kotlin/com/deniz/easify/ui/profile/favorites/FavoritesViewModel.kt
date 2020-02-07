package com.deniz.easify.ui.profile.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result.Success
import com.deniz.easify.data.source.Repository
import com.deniz.easify.data.source.remote.response.TopArtist
import com.deniz.easify.data.source.remote.response.TopTrack
import com.deniz.easify.util.Event
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2019-12-02
 */

class FavoritesViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _topArtist = MutableLiveData<Event<TopArtist>>()
    val topArtist: LiveData<Event<TopArtist>> = _topArtist

    private val _topTracks = MutableLiveData<Event<TopTrack>>()
    val topTracks: LiveData<Event<TopTrack>> = _topTracks

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun fetchTopArtists(type: String, timeRange: String?, limit: Int?) {
        viewModelScope.launch {
            repository.fetchTopArtists(type, timeRange, limit).let { result ->
                if (result is Success) {
                    _topArtist.value = Event(result.data)
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
                    _topTracks.value = Event(result.data)
                } else {
                    _errorMessage.value = "fetch top tracks request'i patladı"
                }
            }
        }
    }
}
