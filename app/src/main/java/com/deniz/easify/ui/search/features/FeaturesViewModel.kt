package com.deniz.easify.ui.search.features

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result.Error
import com.deniz.easify.data.Result.Success
import com.deniz.easify.data.source.Repository
import com.deniz.easify.data.source.remote.parseNetworkError
import com.deniz.easify.data.source.remote.response.FeaturesObject
import com.deniz.easify.data.source.remote.response.Track
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2019-12-31
 */

class FeaturesViewModel(
    private val repository: Repository
) : ViewModel() {

    val track = MutableLiveData<Track>()

    private val _trackFeatures = MutableLiveData<FeaturesObject>()
    val trackFeatures: LiveData<FeaturesObject> = _trackFeatures

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun start(track: Track?) {
        track?.let {
            this.track.value = it
            fetchTrackFeatures(it)
        }
    }

    private fun fetchTrackFeatures(track: Track) {
        viewModelScope.launch {
            repository.fetchTrackFeatures(track.id).let { result ->
                when (result) {
                    is Success -> {
                        _trackFeatures.value = result.data
                    }
                    is Error -> _errorMessage.value = parseNetworkError(result.exception)
                }
            }
        }
    }
}