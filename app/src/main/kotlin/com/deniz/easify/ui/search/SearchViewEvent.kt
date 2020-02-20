package com.deniz.easify.ui.search

import com.deniz.easify.data.source.remote.response.Track

/**
 * @User: deniz.demirci
 * @Date: 2020-02-20
 */

sealed class SearchViewEvent {

    data class OpenTrack(val track: Track) : SearchViewEvent()

    data class OpenPlaylistPage(val track: Track) : SearchViewEvent()

    data class NotifyDataChanged(val trackList: ArrayList<Track>) : SearchViewEvent()

    data class ShowError(val message: String) : SearchViewEvent()
}