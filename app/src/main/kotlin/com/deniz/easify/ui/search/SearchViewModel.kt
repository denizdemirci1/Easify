package com.deniz.easify.ui.search

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result.Error
import com.deniz.easify.data.Result.Success
import com.deniz.easify.data.source.remote.utils.parseNetworkError
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.data.source.repositories.TrackRepository
import com.deniz.easify.ui.search.features.SearchViewEvent
import com.deniz.easify.util.Event
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2019-11-25
 */

class SearchViewModel(
    private val trackRepository: TrackRepository
) : ViewModel() {

    private val _event = MutableLiveData<Event<SearchViewEvent>>()
    val event: LiveData<Event<SearchViewEvent>> = _event

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val tracksToShow = ArrayList<Track>()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun sendEvent(event: SearchViewEvent) {
        _event.value = Event(event)
    }

    fun fetchSongs(q: String) {
        if (q.length < 2)
            return

        viewModelScope.launch {
            trackRepository.fetchTrack(q).let { result ->
                when (result) {
                    is Success -> {
                        tracksToShow.clear()
                        tracksToShow.addAll(result.data.tracks.items)
                        tracksToShow.filter { track ->
                            track.name.contains(q) || track.artists[0].name.contains(q)
                        }
                        sendEvent(SearchViewEvent.NotifyDataChanged(ArrayList(tracksToShow)))
                    }

                    is Error -> {
                        sendEvent(
                            SearchViewEvent.ShowError(
                                parseNetworkError(result.exception)
                            )
                        )
                    }

                }
            }
        }
    }

    /**
     * Called by Data Binding.
     */
    fun openTrack(track: Track) {
        sendEvent(SearchViewEvent.OpenTrack(track))
    }

    fun openPlaylistsPage(track: Track) {
        sendEvent(SearchViewEvent.OpenPlaylistPage(track))
    }
}
