package com.deniz.easify.ui.profile.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result.Success
import com.deniz.easify.data.source.SpotifyRepository
import com.deniz.easify.data.source.remote.response.Track
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2019-12-02
 */

class FavoritesViewModel (
    private val repository: SpotifyRepository
): ViewModel() {

    private val _top = MutableLiveData<Track>()
    val top: LiveData<Track> = _top

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun fetchTop(type: String, timeRange: String?, limit: Int?) {
        viewModelScope.launch {
            repository.fetchTop(type, timeRange, limit).let { result ->
                if (result is Success) {
                    _top.value = result.data
                } else {
                    _errorMessage.value = "fetch top request'i patladı"
                }
            }
        }
    }
}