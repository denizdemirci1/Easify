package com.deniz.easify.ui.profile.followings.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result
import com.deniz.easify.data.source.Repository
import com.deniz.easify.data.source.SpotifyRepository
import com.deniz.easify.data.source.remote.response.Artist
import com.deniz.easify.util.Event
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2019-12-25
 */

class FollowViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _artists = MutableLiveData<ArrayList<Artist>>().apply { value = arrayListOf() }
    val artists: LiveData<ArrayList<Artist>> = _artists

    private val _openArtistFragmentEvent = MutableLiveData<Event<Artist>>()
    val openArtistFragmentEvent: LiveData<Event<Artist>> = _openArtistFragmentEvent

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun fetchArtists(q: String) {
        viewModelScope.launch {
            repository.fetchArtists(q).let { result ->
                if (result is Result.Success) {
                    val artistsToShow = ArrayList<Artist>()
                    artistsToShow.clear()
                    artistsToShow.addAll(result.data.artists.items.filter { it.images.isNotEmpty() })
                    _artists.value = ArrayList(artistsToShow)
                } else {
                    _errorMessage.value = "fetch artists request'i patladı"
                }
            }
        }
    }

    // Called by Data Binding.
    fun openArtistFragment(artist: Artist) {
        _openArtistFragmentEvent.value = Event(artist)
    }
}
