package com.deniz.easify.ui.profile.playlists.detail

import com.deniz.easify.data.source.remote.response.PlaylistTracks
import com.deniz.easify.data.source.remote.response.Track

/**
 * @User: deniz.demirci
 * @Date: 2020-02-24
 */

sealed class PlaylistDetailViewEvent {

    data class NotifyDataChanged(val tracks: ArrayList<PlaylistTracks>) : PlaylistDetailViewEvent()

    data class OpenFeatureFragment(val track: Track) : PlaylistDetailViewEvent()

    data class ShowSnackBar(val trackName: String) : PlaylistDetailViewEvent()

    data class ShowError(val message: String) : PlaylistDetailViewEvent()
}
