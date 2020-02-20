package com.deniz.easify.data

import com.deniz.easify.data.source.remote.response.User
import com.deniz.easify.data.source.repositories.UserRepository

/**
 * @User: deniz.demirci
 * @Date: 2020-02-20
 */

class FakeUserRepository : UserRepository {

    override suspend fun fetchUser(): Result<User>? {
        return Result.Success(
            User(
                "TR",
                "Deniz Demirci",
                "d.demirci93@gmail.com",
                User.ExternalUrl("https://open.spotify.com/user/11131803020"),
                User.Follower(37),
                "11131803020",
                arrayListOf(),
                "premium",
                "user",
                "spotify:user:11131803020"
            )
        )
    }
}