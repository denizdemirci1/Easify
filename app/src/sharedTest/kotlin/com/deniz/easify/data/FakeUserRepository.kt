package com.deniz.easify.data

import com.deniz.easify.data.source.remote.response.User
import com.deniz.easify.data.source.repositories.UserRepository
import org.mockito.Mockito

/**
 * @User: deniz.demirci
 * @Date: 2020-02-20
 */

class FakeUserRepository : UserRepository {

    var shouldReturnError = false
    var fakeTokenRefreshed = false

    override suspend fun fetchUser(): Result<User>? {
        return if (shouldReturnError)
            Result.Error(Exception("fetchUser() failed"))
        else
            Result.Success(Mockito.mock(User::class.java))
    }

    override fun saveToken(accessToken: String?) {
        val fakeToken = accessToken
    }

    override fun getToken() = "token"

    override fun clearToken() {}

    override fun saveUser(user: User?) {}

    override fun getUser(): User? {
        return Mockito.mock(User::class.java)
    }

    override fun setTokenRefreshed(refreshed: Boolean) {
        fakeTokenRefreshed = refreshed
    }

    override fun getTokenRefreshed() = fakeTokenRefreshed
}