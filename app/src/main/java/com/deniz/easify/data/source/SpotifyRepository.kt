package com.deniz.easify.data.source

import com.deniz.easify.data.Result
import com.deniz.easify.data.source.remote.SpotifyService
import com.deniz.easify.data.source.remote.request.PlayBody
import com.deniz.easify.data.source.remote.response.TopArtist
import com.deniz.easify.data.source.remote.response.TopTrack
import com.deniz.easify.data.source.remote.response.Tracks
import com.deniz.easify.data.source.remote.response.User

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

    override suspend fun fetchTrack(q: String): Result<Tracks> {
        return try {
            val track = service.fetchTrack(q)
            Result.Success(track)
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

    override suspend fun play(playBody: PlayBody) {
        try {
            service.play(null, playBody)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
