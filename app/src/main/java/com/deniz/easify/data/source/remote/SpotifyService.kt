package com.deniz.easify.data.source.remote

import com.deniz.easify.data.source.remote.request.PlayBody
import com.deniz.easify.data.source.remote.response.Tracks
import com.deniz.easify.data.source.remote.response.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

/**
 * @User: deniz.demirci
 * @Date: 2019-11-11
 */

interface SpotifyService {

    companion object {
        const val baseUrl = "https://api.spotify.com/v1/"

        const val query_q = "q"
        const val query_type = "type"
        const val query_limit = "limit"
        const val query_device_id = "device_id"
    }

    @GET("me")
    suspend fun fetchUser(): User

    @GET("search")
    suspend fun fetchTrack(@Query(query_q) q: String,
                           @Query(query_type) type: String = "track",
                           @Query(query_limit) limit: Int = 10): Tracks

    @PUT("me/player/play")
    suspend fun play(@Query(query_device_id) deviceId: String? = "d93c8e8670a85a59f9d182051a79893c956d8e06",
                     @Body request: PlayBody)
}
