package com.deniz.easify.ui.search.features.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deniz.easify.data.source.Repository
import com.deniz.easify.data.source.remote.response.FeaturesObject

/**
 * @User: deniz.demirci
 * @Date: 2020-01-02
 */

class DiscoverViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _trackFeatures = MutableLiveData<FeaturesObject>()
    val trackFeatures: LiveData<FeaturesObject> = _trackFeatures

    private var seedTrackId: String? = null

    fun start(features: FeaturesObject?) {
        features?.let {
            _trackFeatures.value = it
            seedTrackId = it.id
        }
    }

    fun fetchRecommendations(
        danceability: Float,
        energy: Float,
        speechiness: Float,
        acousticness: Float,
        instrumentalness: Float,
        liveness: Float,
        valence: Float,
        tempo: Float
    ) {

    }
}
