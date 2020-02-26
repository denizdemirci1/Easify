package com.deniz.easify.ui.profile.favorites

import com.deniz.easify.data.source.remote.response.TopArtist
import com.deniz.easify.data.source.remote.response.TopTrack

/**
 * @User: deniz.demirci
 * @Date: 2020-02-23
 */

sealed class FavoritesViewEvent {

    data class OpenTopArtists(val topArtist: TopArtist) : FavoritesViewEvent()

    data class OpenTopTracks(val topTrack: TopTrack) : FavoritesViewEvent()

    data class ShowError(val message: String) : FavoritesViewEvent()
}
