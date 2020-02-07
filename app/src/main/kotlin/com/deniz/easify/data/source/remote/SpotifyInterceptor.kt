package com.deniz.easify.data.source.remote

import com.deniz.easify.util.AuthManager
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @User: deniz.demirci
 * @Date: 2019-11-11
 */

class SpotifyInterceptor(private val authManager: AuthManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        val token: String? = authManager.token ?: ""
        token?.let { builder.addHeader("Authorization", "Bearer $it") }

        val request = builder.build()
        return chain.proceed(request)
    }
}
