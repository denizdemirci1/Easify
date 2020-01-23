package com.deniz.easify.data.source

import com.deniz.easify.data.Result
import com.deniz.easify.data.source.remote.request.PlayBody
import com.deniz.easify.data.source.remote.request.RemoveTracksBody
import com.deniz.easify.data.source.remote.response.*

/**
 * @User: deniz.demirci
 * @Date: 2019-11-27
 */

interface Repository {

    suspend fun fetchUser(): Result<User>?

    suspend fun fetchTrack(q: String): Result<TracksObject>

    suspend fun fetchTrackFeatures(id: String): Result<FeaturesObject>

    suspend fun fetchArtists(q: String): Result<ArtistsResponse>

    suspend fun fetchTopArtists(type: String, timeRange: String?, limit: Int?): Result<TopArtist>

    suspend fun fetchTopTracks(type: String, timeRange: String?, limit: Int?): Result<TopTrack>

    suspend fun fetchFollowedArtists(): Result<ArtistsResponse>

    suspend fun play(playBody: PlayBody)

    suspend fun pause()

    suspend fun followArtist(id: String)

    suspend fun unfollowArtist(id: String)

    suspend fun fetchPlaylists(id: String): Result<PlaylistObject>

    suspend fun fetchPlaylistTracks(id: String): Result<PlaylistTracksObject>

    suspend fun removeTrackFromPlaylist(id: String, removeTracksBody: RemoveTracksBody)

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
