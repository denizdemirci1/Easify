package com.deniz.easify.data.source.repositories

import androidx.annotation.VisibleForTesting
import com.deniz.easify.data.Result
import com.deniz.easify.data.source.remote.service.SpotifyService
import com.deniz.easify.data.source.remote.response.User

/**
 * @User: deniz.demirci
 * @Date: 2020-02-20
 */

interface UserRepository {

    suspend fun fetchUser(): Result<User>?
}

class DefaultUserRepository(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal val service: SpotifyService
) : UserRepository {

    override suspend fun fetchUser(): Result<User>? {
        return try {
            val user = service.fetchUser()
            Result.Success(user)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}