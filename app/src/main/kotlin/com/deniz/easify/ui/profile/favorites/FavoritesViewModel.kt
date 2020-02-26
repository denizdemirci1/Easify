package com.deniz.easify.ui.profile.favorites

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result.Error
import com.deniz.easify.data.Result.Success
import com.deniz.easify.data.source.remote.utils.parseNetworkError
import com.deniz.easify.data.source.repositories.PersonalizationRepository
import com.deniz.easify.util.Event
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2019-12-02
 */

class FavoritesViewModel(
    private val personalizationRepository: PersonalizationRepository
) : ViewModel() {

    private val _event = MutableLiveData<Event<FavoritesViewEvent>>()
    val event: LiveData<Event<FavoritesViewEvent>> = _event

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun sendEvent(event: FavoritesViewEvent) {
        _event.value = Event(event)
    }

    fun fetchTopArtists(type: String, timeRange: String?, limit: Int?) {
        viewModelScope.launch {
            personalizationRepository.fetchTopArtists(type, timeRange, limit).let { result ->
                when (result) {
                    is Success -> {
                        sendEvent(FavoritesViewEvent.OpenTopArtists(result.data))
                    }

                    is Error -> {
                        sendEvent(FavoritesViewEvent.ShowError(parseNetworkError(result.exception)))
                    }
                }
            }
        }
    }

    fun fetchTopTracks(type: String, timeRange: String?, limit: Int?) {
        viewModelScope.launch {
            personalizationRepository.fetchTopTracks(type, timeRange, limit).let { result ->
                when (result) {
                    is Success -> {
                        sendEvent(FavoritesViewEvent.OpenTopTracks(result.data))
                    }

                    is Error -> {
                        sendEvent(FavoritesViewEvent.ShowError(parseNetworkError(result.exception)))
                    }
                }
            }
        }
    }
}
