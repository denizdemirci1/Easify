package com.deniz.easify.data.source.repositories

import androidx.annotation.VisibleForTesting
import com.deniz.easify.data.Result
import com.deniz.easify.data.source.remote.response.ArtistsResponse
import com.deniz.easify.data.source.remote.service.SpotifyService

/**
 * @User: deniz.demirci
 * @Date: 2020-02-20
 */

interface ArtistRepository {

    suspend fun fetchArtists(q: String): Result<ArtistsResponse>
}

class DefaultArtistRepository(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal val service: SpotifyService
) : ArtistRepository {

    override suspend fun fetchArtists(q: String): Result<ArtistsResponse> {
        return try {
            val artists = service.fetchArtists("$q*")
            Result.Success(artists)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
