package com.deniz.easify.ui.profile.favorites.topTracks

import com.deniz.easify.data.source.remote.response.TopTrack
import com.deniz.easify.data.source.remote.response.Track

/**
 * @User: deniz.demirci
 * @Date: 2020-02-23
 */

sealed class TopTracksViewEvent {

    data class OpenFeaturesFragment(val track: Track): TopTracksViewEvent()

    data class OpenPlaylistsFragment(val track: Track): TopTracksViewEvent()

    data class NotifyDataChanged(val topTrack: TopTrack): TopTracksViewEvent()


}