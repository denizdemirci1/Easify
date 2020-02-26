package com.deniz.easify.ui.profile.followings.artist

/**
 * @User: deniz.demirci
 * @Date: 2020-02-23
 */

sealed class ArtistViewEvent {

    enum class Follow {
        FOLLOW, UNFOLLOW
    }
}
