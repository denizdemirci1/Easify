package com.deniz.easify.data.source.repositories

import androidx.annotation.VisibleForTesting
import com.deniz.easify.data.Result
import com.deniz.easify.data.source.remote.response.ArtistsResponse
import com.deniz.easify.data.source.remote.service.SpotifyService

/**
 * @User: deniz.demirci
 * @Date: 2020-02-20
 */

interface FollowRepository {

    suspend fun fetchFollowedArtists(): Result<ArtistsResponse>

    suspend fun followArtist(id: String)

    suspend fun unfollowArtist(id: String)
}

class DefaultFollowRepository(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal val service: SpotifyService
) : FollowRepository {

    override suspend fun fetchFollowedArtists(): Result<ArtistsResponse> {
        return try {
            val followedArtists = service.fetchFollowedArtists()
            Result.Success(followedArtists)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun followArtist(id: String) {
        try {
            service.followArtist(id = id)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun unfollowArtist(id: String) {
        try {
            service.unfollowArtist(id = id)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
