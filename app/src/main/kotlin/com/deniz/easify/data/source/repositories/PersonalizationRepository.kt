package com.deniz.easify.data.source.repositories

import androidx.annotation.VisibleForTesting
import com.deniz.easify.data.Result
import com.deniz.easify.data.source.remote.response.TopArtist
import com.deniz.easify.data.source.remote.response.TopTrack
import com.deniz.easify.data.source.remote.service.SpotifyService

/**
 * @User: deniz.demirci
 * @Date: 2020-02-20
 */

interface PersonalizationRepository {

    suspend fun fetchTopArtists(type: String, timeRange: String?, limit: Int?): Result<TopArtist>

    suspend fun fetchTopTracks(type: String, timeRange: String?, limit: Int?): Result<TopTrack>
}

class DefaultPersonalizationRepository(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal val service: SpotifyService
) : PersonalizationRepository {

    override suspend fun fetchTopArtists(
        type: String,
        timeRange: String?,
        limit: Int?
    ): Result<TopArtist> {
        return try {
            val topArtist = service.fetchTopArtists(type, timeRange, limit)
            Result.Success(topArtist)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun fetchTopTracks(
        type: String,
        timeRange: String?,
        limit: Int?
    ): Result<TopTrack> {
        return try {
            val topTrack = service.fetchTopTracks(type, timeRange, limit)
            Result.Success(topTrack)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
