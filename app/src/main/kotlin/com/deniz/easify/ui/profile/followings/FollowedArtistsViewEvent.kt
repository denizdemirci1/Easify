package com.deniz.easify.ui.profile.followings

import com.deniz.easify.data.source.remote.response.Artist

/**
 * @User: deniz.demirci
 * @Date: 2020-02-24
 */

sealed class FollowedArtistsViewEvent {

    data class NotifyDataChanged(val artists: ArrayList<Artist>) : FollowedArtistsViewEvent()

    data class ShowError(val message: String) : FollowedArtistsViewEvent()

    data class OpenArtistFragment(val artist: Artist) : FollowedArtistsViewEvent()
}
