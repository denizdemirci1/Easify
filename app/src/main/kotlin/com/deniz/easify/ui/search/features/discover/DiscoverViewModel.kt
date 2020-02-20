package com.deniz.easify.ui.search.features.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.easify.data.Result.Error
import com.deniz.easify.data.Result.Success
import com.deniz.easify.data.source.Repository
import com.deniz.easify.data.source.remote.parseNetworkError
import com.deniz.easify.data.source.remote.response.FeaturesObject
import com.deniz.easify.data.source.remote.response.RecommendationsObject
import com.deniz.easify.util.Event
import kotlinx.coroutines.launch

/**
 * @User: deniz.demirci
 * @Date: 2020-01-02
 */

class DiscoverViewModel: ViewModel() {

    private val _trackFeatures = MutableLiveData<FeaturesObject>()
    val trackFeatures: LiveData<FeaturesObject> = _trackFeatures

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _showDiscoverButton = MutableLiveData<Boolean>().apply { value = true }
    val showDiscoverButton: LiveData<Boolean> = _showDiscoverButton

    fun start(features: FeaturesObject?) {
        features?.let {
            _trackFeatures.value = it
            return
        }
        _showDiscoverButton.value = false
    }
}
