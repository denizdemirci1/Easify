package com.deniz.easify.ui.history

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result
import com.deniz.easify.data.source.Repository
import com.deniz.easify.data.source.remote.parseNetworkError
import com.deniz.easify.data.source.remote.response.History
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.util.Event
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2020-01-24
 */

class HistoryViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _historyList = MutableLiveData<ArrayList<History>>().apply { value = arrayListOf() }
    val historyList: LiveData<ArrayList<History>> = _historyList

    private val _openTrackEvent = MutableLiveData<Event<Track>>()
    val openTrackEvent: LiveData<Event<Track>> = _openTrackEvent

    private val _openPlaylistsPageEvent = MutableLiveData<Event<Track>>()
    val openPlaylistsPageEvent: LiveData<Event<Track>> = _openPlaylistsPageEvent

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val historyToShow = ArrayList<History>()

    fun fetchRecentlyPlayedSongs() {
        viewModelScope.launch {
            repository.fetchRecentlyPlayed().let { result ->
                when (result) {
                    is Result.Success -> {
                        historyToShow.clear()
                        historyToShow.addAll(
                            result.data.history.distinctBy { it.track.id }
                        )
                        _historyList.value = ArrayList(historyToShow)
                    }
                    is Result.Error -> _errorMessage.value = parseNetworkError(result.exception)
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

    fun openPlaylistsPage(track: Track) {
        _openPlaylistsPageEvent.value = Event(track)
    }
}
