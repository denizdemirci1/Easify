package com.deniz.easify.ui.profile.followings.follow

import com.deniz.easify.data.source.remote.response.Artist

/**
 * @User: deniz.demirci
 * @Date: 2020-02-24
 */

sealed class FollowViewEvent {

    data class NotifyDataChanged(val artists: ArrayList<Artist>) : FollowViewEvent()

    data class ShowError(val message: String) : FollowViewEvent()

    data class OpenArtistFragment(val artist: Artist) : FollowViewEvent()
}
