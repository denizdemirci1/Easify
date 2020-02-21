package com.deniz.easify.data.source.repositories

import androidx.annotation.VisibleForTesting
import com.deniz.easify.data.Result
import com.deniz.easify.data.source.remote.service.SpotifyService
import com.deniz.easify.data.source.remote.response.User
import com.deniz.easify.util.AuthManager

/**
 * @User: deniz.demirci
 * @Date: 2020-02-20
 */

interface UserRepository {

    suspend fun fetchUser(): Result<User>?

    fun saveToken(accessToken: String?)

    fun getToken(): String?

    fun clearToken()

    fun saveUser(user: User?)

    fun getUser(): User?

    fun setTokenRefreshed(refreshed: Boolean)

    fun getTokenRefreshed(): Boolean
}

class DefaultUserRepository(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal val service: SpotifyService,
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal val authManager: AuthManager
) : UserRepository {

    override suspend fun fetchUser(): Result<User>? {
        return try {
            val user = service.fetchUser()
            Result.Success(user)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override fun saveToken(accessToken: String?) {
        authManager.token = accessToken
    }

    override fun getToken(): String? {
        return authManager.token
    }

    override fun clearToken() {
        authManager.token = ""
    }

    override fun saveUser(user: User?) {
        authManager.user = user
    }

    override fun getUser(): User? {
        return authManager.user
    }

    override fun setTokenRefreshed(refreshed: Boolean) {
        authManager.tokenRefreshed = refreshed
    }

    override fun getTokenRefreshed(): Boolean {
        return authManager.tokenRefreshed
    }


}