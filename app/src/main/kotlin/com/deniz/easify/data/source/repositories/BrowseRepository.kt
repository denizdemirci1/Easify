package com.deniz.easify.data.source.repositories

import androidx.annotation.VisibleForTesting
import com.deniz.easify.data.Result
import com.deniz.easify.data.source.remote.response.RecommendationsObject
import com.deniz.easify.data.source.remote.service.SpotifyService

/**
 * @User: deniz.demirci
 * @Date: 2020-02-20
 */

interface BrowseRepository {

    suspend fun fetchRecommendations(
        danceability: Float,
        energy: Float,
        speechiness: Float,
        acousticness: Float,
        instrumentalness: Float,
        liveness: Float,
        valence: Float,
        tempo: Float,
        seedTrackId: String
    ): Result<RecommendationsObject>
}

class DefaultBrowseRepository(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal val service: SpotifyService
) : BrowseRepository {

    override suspend fun fetchRecommendations(
        danceability: Float,
        energy: Float,
        speechiness: Float,
        acousticness: Float,
        instrumentalness: Float,
        liveness: Float,
        valence: Float,
        tempo: Float,
        seedTrackId: String
    ): Result<RecommendationsObject> {
        return try {
            val recommendations = service.fetchRecommendations(
                seedTrackId,
                acousticness,
                danceability,
                energy,
                instrumentalness,
                liveness,
                speechiness,
                tempo,
                valence
            )
            Result.Success(recommendations)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
