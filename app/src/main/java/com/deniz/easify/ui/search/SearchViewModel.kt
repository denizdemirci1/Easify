package com.deniz.easify.ui.search

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result.Error
import com.deniz.easify.data.Result.Success
import com.deniz.easify.data.source.Repository
import com.deniz.easify.data.source.remote.parseNetworkError
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.util.Event
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2019-11-25
 */

class SearchViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _trackList = MutableLiveData<ArrayList<Track>>().apply { value = arrayListOf() }
    val trackList: LiveData<ArrayList<Track>> = _trackList

    private val _openTrackEvent = MutableLiveData<Event<Track>>()
    val openTrackEvent: LiveData<Event<Track>> = _openTrackEvent

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val tracksToShow = ArrayList<Track>()

    fun fetchSongs(q: String) {
        if (q.length < 2)
            return

        viewModelScope.launch {
            repository.fetchTrack(q).let { result ->
                when (result) {
                    is Success -> {
                        tracksToShow.clear()
                        tracksToShow.addAll(result.data.tracks.items)
                        tracksToShow.filter { track ->
                            track.name.contains(q) || track.artists[0].name.contains(q)
                        }
                        _trackList.value = ArrayList(tracksToShow)
                    }
                    is Error -> _errorMessage.value = parseNetworkError(result.exception)
                }
            }
        }
    }

    /**
     * Called by Data Binding.
     */
    fun openTrack(track: Track) {
        _openTrackEvent.value = Event(track)
    }
}
