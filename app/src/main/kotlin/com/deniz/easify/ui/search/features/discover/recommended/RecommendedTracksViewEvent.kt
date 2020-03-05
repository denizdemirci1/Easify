package com.deniz.easify.ui.search.features.discover.recommended

import com.deniz.easify.data.source.remote.response.Track

/**
 * @User: deniz.demirci
 * @Date: 2020-02-24
 */

sealed class RecommendedTracksViewEvent {

    data class SetTitle(val size: Int) : RecommendedTracksViewEvent()

    data class NotifyDataChanged(val recommendations: ArrayList<Track>) : RecommendedTracksViewEvent()

    data class ShowSnackBar(val isSuccessful: Boolean) : RecommendedTracksViewEvent()

    data class ShowError(val message: String) : RecommendedTracksViewEvent()

    data class OpenTrackOnSpotify(val track: Track) : RecommendedTracksViewEvent()

    object OpenPlaylistsFragment : RecommendedTracksViewEvent()
}
