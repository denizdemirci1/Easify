package com.deniz.easify.ui.history

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result
import com.deniz.easify.data.source.remote.utils.parseNetworkError
import com.deniz.easify.data.source.remote.response.History
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.data.source.repositories.PlayerRepository
import com.deniz.easify.util.Event
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2020-01-24
 */

class HistoryViewModel(
    private val playerRepository: PlayerRepository
) : ViewModel() {

    private val _event = MutableLiveData<Event<HistoryViewEvent>>()
    val event: LiveData<Event<HistoryViewEvent>> = _event

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val historyToShow = ArrayList<History>()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun sendEvent(event: HistoryViewEvent) {
        _event.value = Event(event)
    }

    fun fetchRecentlyPlayedSongs() {
        viewModelScope.launch {
            playerRepository.fetchRecentlyPlayed().let { result ->
                when (result) {
                    is Result.Success -> {
                        historyToShow.clear()
                        historyToShow.addAll(result.data.history.distinctBy { it.track.id })
                        sendEvent(HistoryViewEvent.NotifyDataChanged(ArrayList(historyToShow)))
                    }

                    is Result.Error -> {
                        sendEvent(HistoryViewEvent.ShowError(parseNetworkError(result.exception)))
                    }
                }
            }
        }
    }

    /**
     * Called by Data Binding.
     */
    fun openTrack(track: Track) {
        sendEvent(HistoryViewEvent.OpenFeaturesFragment(track))
    }

    fun openPlaylistsPage(track: Track) {
        sendEvent(HistoryViewEvent.OpenPlaylistsFragment(track))
    }
}
