package com.deniz.easify.data.source.repositories

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PRIVATE
import com.deniz.easify.data.Result
import com.deniz.easify.data.source.remote.response.FeaturesObject
import com.deniz.easify.data.source.remote.response.TracksObject
import com.deniz.easify.data.source.remote.service.SpotifyService

/**
 * @User: deniz.demirci
 * @Date: 2020-02-20
 */

interface TrackRepository {

    suspend fun fetchTrack(q: String): Result<TracksObject>

    suspend fun fetchTrackFeatures(id: String): Result<FeaturesObject>
}

class DefaultTrackRepository(
    @VisibleForTesting(otherwise = PRIVATE)
    internal val service: SpotifyService
) : TrackRepository {

    override suspend fun fetchTrack(q: String): Result<TracksObject> {
        return try {
            val track = service.fetchTrack("$q*")
            Result.Success(track)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun fetchTrackFeatures(id: String): Result<FeaturesObject> {
        return try {
            val trackFeatures = service.fetchTrackFeatures(id)
            Result.Success(trackFeatures)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
