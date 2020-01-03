package com.deniz.easify.data.source

import com.deniz.easify.data.Result
import com.deniz.easify.data.source.remote.SpotifyService
import com.deniz.easify.data.source.remote.request.PlayBody
import com.deniz.easify.data.source.remote.response.*

/**
 * @User: deniz.demirci
 * @Date: 2019-11-11
 */

class SpotifyRepository(private val service: SpotifyService) : Repository {

    override suspend fun fetchUser(): Result<User>? {
        return try {
            val user = service.fetchUser()
            Result.Success(user)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

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

    override suspend fun fetchArtists(q: String): Result<ArtistsResponse> {
        return try {
            val artists = service.fetchArtists("$q*")
            Result.Success(artists)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

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

    override suspend fun fetchFollowedArtists(): Result<ArtistsResponse> {
        return try {
            val followedArtists = service.fetchFollowedArtists()
            Result.Success(followedArtists)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun play(playBody: PlayBody) {
        try {
            service.play(playBody)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun pause() {
        try {
            service.pause()
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
            val recommendations = service.fetchRecommendations(seedTrackId, acousticness, danceability, energy, instrumentalness, liveness, speechiness, tempo, valence)
            Result.Success(recommendations)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
