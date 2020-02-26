package com.deniz.easify.ui.profile.favorites.topArtists

import com.deniz.easify.data.source.remote.response.Artist
import com.deniz.easify.data.source.remote.response.TopArtist

/**
 * @User: deniz.demirci
 * @Date: 2020-02-23
 */

sealed class TopArtistsViewEvent {

    data class OpenArtistFragment(val artist: Artist) : TopArtistsViewEvent()

    data class NotifyDataChanged(val topArtist: TopArtist) : TopArtistsViewEvent()
}
