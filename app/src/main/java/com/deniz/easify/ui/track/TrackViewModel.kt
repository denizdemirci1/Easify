package com.deniz.easify.ui.track

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.source.remote.response.Item
import com.deniz.easify.data.source.SpotifyRepository
import com.deniz.easify.data.source.remote.request.PlayBody
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2019-11-26
 */

class TrackViewModel(
    private val repository: SpotifyRepository
) : ViewModel() {

    val track = MutableLiveData<Item>()

    private val _empty = MutableLiveData<Boolean>()
    val empty: LiveData<Boolean> = _empty

    fun start(track: Item?) {
        _empty.value = track == null

        if (track != null) {
            this.track.value = track
            playTrack()
        }
    }

    private fun playTrack(){
        viewModelScope.launch {
            val playBody = arrayListOf<String>()
            playBody.add(track.value!!.uri)
            repository.play(PlayBody(playBody))
        }
    }
}