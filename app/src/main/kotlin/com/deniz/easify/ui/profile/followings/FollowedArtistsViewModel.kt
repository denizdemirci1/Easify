package com.deniz.easify.ui.profile.followings

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result.Error
import com.deniz.easify.data.Result.Success
import com.deniz.easify.data.source.remote.utils.parseNetworkError
import com.deniz.easify.data.source.remote.response.Artist
import com.deniz.easify.data.source.repositories.FollowRepository
import com.deniz.easify.util.Event
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2019-12-25
 */

class FollowedArtistsViewModel(
    private val followRepository: FollowRepository
) : ViewModel() {

    private val _event = MutableLiveData<Event<FollowedArtistsViewEvent>>()
    val event: LiveData<Event<FollowedArtistsViewEvent>> = _event

    private val _loading = MutableLiveData<Boolean>(true)
    val loading: LiveData<Boolean> = _loading

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun sendEvent(event: FollowedArtistsViewEvent) {
        _event.value = Event(event)
    }

    fun fetchFollowedArtists() {
        viewModelScope.launch {
            followRepository.fetchFollowedArtists().let { result ->
                when (result) {
                    is Success -> {
                        _loading.value = false
                        sendEvent(
                            FollowedArtistsViewEvent.NotifyDataChanged(
                            result.data.artists.items)
                        )

                    }

                    is Error -> {
                        _loading.value = false
                        sendEvent(
                            FollowedArtistsViewEvent.ShowError(
                            parseNetworkError(result.exception))
                        )
                    }
                }
            }
        }
    }

    // Called by Data Binding.
    fun openArtistFragment(artist: Artist) {
        sendEvent(FollowedArtistsViewEvent.OpenArtistFragment(artist))
    }
}
