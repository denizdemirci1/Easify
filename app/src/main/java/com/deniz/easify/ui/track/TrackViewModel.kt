package com.deniz.easify.ui.track

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.source.Repository
import com.deniz.easify.data.source.remote.request.PlayBody
import com.deniz.easify.data.source.remote.response.Track
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2019-11-26
 */

class TrackViewModel(
    private val repository: Repository
) : ViewModel() {

    val track = MutableLiveData<Track>()

    private val _empty = MutableLiveData<Boolean>()
    val empty: LiveData<Boolean> = _empty

    fun start(track: Track?) {
        _empty.value = track == null

        if (track != null) {
            this.track.value = track
        }
    }

    fun playTrack() {
        viewModelScope.launch {
            val playBody = mutableListOf<String>()
            playBody.add(track.value!!.uri)
            repository.play(PlayBody(playBody))
        }
    }

    fun pauseTrack() {
        viewModelScope.launch {
            repository.pause()
        }
    }
}
