package com.deniz.easify.ui.profile.playlists

import com.deniz.easify.data.source.remote.response.Playlist

/**
 * @User: deniz.demirci
 * @Date: 2020-02-24
 */

sealed class PlaylistViewEvent {

    data class SetTitle(val reason: PlaylistViewModel.Reason) : PlaylistViewEvent()

    data class NotifyDataChanged(val playlists: ArrayList<Playlist>) : PlaylistViewEvent()

    data class TrackAddingSucceeded(val trackName: String, val playlistName: String) : PlaylistViewEvent()

    data class TrackAddingFailed(val trackName: String, val playlistName: String) : PlaylistViewEvent()

    data class OpenPlaylistDetail(val playlist: Playlist, val isEditable: Boolean) : PlaylistViewEvent()

    data class FetchPlaylistTracks(val playlist: Playlist) : PlaylistViewEvent()

    data class ShowError(val message: String) : PlaylistViewEvent()
}
