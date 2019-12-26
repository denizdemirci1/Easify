package com.deniz.easify.data.source

import com.deniz.easify.data.Result
import com.deniz.easify.data.source.remote.request.PlayBody
import com.deniz.easify.data.source.remote.response.ArtistsResponse
import com.deniz.easify.data.source.remote.response.TopArtist
import com.deniz.easify.data.source.remote.response.TopTrack
import com.deniz.easify.data.source.remote.response.TracksObject
import com.deniz.easify.data.source.remote.response.User

/**
 * @User: deniz.demirci
 * @Date: 2019-11-27
 */

interface Repository {

    suspend fun fetchUser(): Result<User>?

    suspend fun fetchTrack(q: String): Result<TracksObject>

    suspend fun fetchArtists(q: String): Result<ArtistsResponse>

    suspend fun fetchTopArtists(type: String, timeRange: String?, limit: Int?): Result<TopArtist>

    suspend fun fetchTopTracks(type: String, timeRange: String?, limit: Int?): Result<TopTrack>

    suspend fun fetchFollowedArtists(): Result<ArtistsResponse>

    suspend fun play(playBody: PlayBody)

    suspend fun followArtist(id: String)

    suspend fun unfollowArtist(id: String)
}
