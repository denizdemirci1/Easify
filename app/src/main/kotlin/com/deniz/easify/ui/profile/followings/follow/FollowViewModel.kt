package com.deniz.easify.ui.profile.followings.follow

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result.Error
import com.deniz.easify.data.Result.Success
import com.deniz.easify.data.source.remote.response.Artist
import com.deniz.easify.data.source.remote.utils.parseNetworkError
import com.deniz.easify.data.source.repositories.ArtistRepository
import com.deniz.easify.util.Event
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2019-12-25
 */

class FollowViewModel(
    private val artistRepository: ArtistRepository
) : ViewModel() {

    private val _event = MutableLiveData<Event<FollowViewEvent>>()
    val event: LiveData<Event<FollowViewEvent>> = _event

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun sendEvent(event: FollowViewEvent) {
        _event.value = Event(event)
    }

    private val _showClearIcon = MutableLiveData<Boolean>(false)
    val showClearIcon: LiveData<Boolean> = _showClearIcon

    fun fetchArtists(q: String) {
        viewModelScope.launch {
            artistRepository.fetchArtists(q).let { result ->
                when (result) {
                    is Success -> {
                        val artistsToShow = ArrayList<Artist>()
                        artistsToShow.clear()
                        artistsToShow.addAll(result.data.artists.items)
                        sendEvent(FollowViewEvent.NotifyDataChanged(ArrayList(artistsToShow)))
                    }

                    is Error -> {
                        sendEvent(FollowViewEvent.ShowError(parseNetworkError(result.exception)))
                    }
                }
            }
        }
    }

    // Called by Data Binding.
    fun openArtistFragment(artist: Artist) {
        sendEvent(FollowViewEvent.OpenArtistFragment(artist))
    }

    fun showClearIcon(show: Boolean) {
        _showClearIcon.value = show
    }
}
