package com.deniz.easify.ui.profile.followings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result.Error
import com.deniz.easify.data.Result.Success
import com.deniz.easify.data.source.Repository
import com.deniz.easify.data.source.remote.parseNetworkError
import com.deniz.easify.data.source.remote.response.Artist
import com.deniz.easify.util.Event
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2019-12-25
 */

class FollowedArtistsViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _artists = MutableLiveData<ArrayList<Artist>>().apply { value = arrayListOf() }
    val artists: LiveData<ArrayList<Artist>> = _artists

    private val _openArtistFragmentEvent = MutableLiveData<Event<Artist>>()
    val openArtistFragmentEvent: LiveData<Event<Artist>> = _openArtistFragmentEvent

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun fetchFollowedArtists() {
        viewModelScope.launch {
            repository.fetchFollowedArtists().let { result ->
                when (result) {
                    is Success -> {
                        _artists.value = result.data.artists.items
                    }
                    is Error -> _errorMessage.value = parseNetworkError(result.exception)
                }
            }
        }
    }

    // Called by Data Binding.
    fun openArtistFragment(artist: Artist) {
        _openArtistFragmentEvent.value = Event(artist)
    }
}
