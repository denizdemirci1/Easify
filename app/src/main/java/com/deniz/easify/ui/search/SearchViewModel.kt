package com.deniz.easify.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.source.remote.response.Item
import com.deniz.easify.data.Result.Success
import com.deniz.easify.data.source.SpotifyRepository
import com.deniz.easify.util.Event
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2019-11-25
 */

class SearchViewModel(
    private val repository: SpotifyRepository
) : ViewModel() {

    private val _trackList = MutableLiveData<ArrayList<Item>>().apply { value = arrayListOf() }
    val trackList: LiveData<ArrayList<Item>> = _trackList

    private val _openTrackEvent = MutableLiveData<Event<Item>>()
    val openTrackEvent: LiveData<Event<Item>> = _openTrackEvent

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage


    fun fetchSongs(q: String) {
        viewModelScope.launch {
            repository.fetchTrack(q).let { result ->
                if (result is Success) {
                    val tracksToShow = ArrayList<Item>()
                    tracksToShow.clear()
                    tracksToShow.addAll(result.data.tracks.items)
                    _trackList.value = ArrayList(tracksToShow)
                } else {
                    _errorMessage.value = "fetch song request'i patladı"
                }
            }
        }
    }

    /**
     * Called by Data Binding.
     */
    fun openTrack(track: Item) {
        _openTrackEvent.value = Event(track)
    }

}