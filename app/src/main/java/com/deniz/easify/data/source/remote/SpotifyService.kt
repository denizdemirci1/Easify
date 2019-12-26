package com.deniz.easify.data.source.remote

import com.deniz.easify.data.source.remote.request.PlayBody
import com.deniz.easify.data.source.remote.response.*
import retrofit2.http.*

/**
 * @User: deniz.demirci
 * @Date: 2019-11-11
 */

interface SpotifyService {

    companion object {
        const val baseUrl = "https://api.spotify.com/v1/"

        const val query_q = "q"
        const val query_type = "type"
        const val query_ids = "ids"
        const val query_limit = "limit"
        const val query_device_id = "device_id"
        const val query_time_range = "time_range"

        const val path_type = "type"
    }

    @GET("me")
    suspend fun fetchUser(): User

    @GET("search")
    suspend fun fetchTrack(
        @Query(query_q) q: String,
        @Query(query_type) type: String = "track",
        @Query(query_limit) limit: Int = 50
    ): TracksObject

    @GET("search")
    suspend fun fetchArtists(
        @Query(query_q) q: String,
        @Query(query_type) type: String = "artist",
        @Query(query_limit) limit: Int = 50
    ): ArtistsResponse

    @PUT("me/player/play")
    suspend fun play(
        @Query(query_device_id) deviceId: String? = "d93c8e8670a85a59f9d182051a79893c956d8e06",
        @Body request: PlayBody
    )

    @PUT("me/following")
    suspend fun followArtist(
        @Query(query_type) type: String? = "artist",
        @Query(query_ids) id: String
    )

    @DELETE("me/following")
    suspend fun unfollowArtist(
        @Query(query_type) type: String? = "artist",
        @Query(query_ids) id: String
    )

    @GET("me/top/{type}")
    suspend fun fetchTopArtists(
        @Path(path_type) type: String? = "artists",
        @Query(query_time_range) timeRange: String?,
        @Query(query_limit) limit: Int? = 20
    ): TopArtist

    @GET("me/top/{type}")
    suspend fun fetchTopTracks(
        @Path(path_type) type: String? = "tracks",
        @Query(query_time_range) timeRange: String?,
        @Query(query_limit) limit: Int? = 20
    ): TopTrack

    @GET("me/following")
    suspend fun fetchFollowedArtists(
        @Query(query_type) type: String = "artist",
        @Query(query_limit) limit: Int = 50
    ): ArtistsResponse
}
