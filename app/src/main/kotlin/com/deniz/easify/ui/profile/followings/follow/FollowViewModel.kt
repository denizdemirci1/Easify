package com.deniz.easify.ui.profile.followings.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result.Error
import com.deniz.easify.data.Result.Success
import com.deniz.easify.data.source.remote.utils.parseNetworkError
import com.deniz.easify.data.source.remote.response.Artist
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

    private val _artists = MutableLiveData<ArrayList<Artist>>().apply { value = arrayListOf() }
    val artists: LiveData<ArrayList<Artist>> = _artists

    private val _openArtistFragmentEvent = MutableLiveData<Event<Artist>>()
    val openArtistFragmentEvent: LiveData<Event<Artist>> = _openArtistFragmentEvent

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun fetchArtists(q: String) {
        viewModelScope.launch {
            artistRepository.fetchArtists(q).let { result ->
                when (result) {
                    is Success -> {
                        val artistsToShow = ArrayList<Artist>()
                        artistsToShow.clear()
                        artistsToShow.addAll(result.data.artists.items)
                        _artists.value = ArrayList(artistsToShow)
                    }
                    is Error -> _errorMessage.value =
                        parseNetworkError(
                            result.exception
                        )
                }
            }
        }
    }

    // Called by Data Binding.
    fun openArtistFragment(artist: Artist) {
        _openArtistFragmentEvent.value = Event(artist)
    }
}
