package com.deniz.easify.data.source.remote.service

import com.deniz.easify.data.source.remote.request.CreatePlaylistBody
import com.deniz.easify.data.source.remote.request.PlayBody
import com.deniz.easify.data.source.remote.request.RemoveTracksBody
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
        const val query_uris = "uris"
        const val query_limit = "limit"
        const val query_time_range = "time_range"
        const val query_offset = "offset"

        //region recommendations
        const val query_seed_tracks = "seed_tracks"
        const val query_target_acousticness = "target_acousticness"
        const val query_target_danceability = "target_danceability"
        const val query_target_energy = "target_energy"
        const val query_target_instrumentalness = "target_instrumentalness"
        const val query_target_liveness = "target_liveness"
        const val query_target_speechiness = "target_speechiness"
        const val query_target_tempo = "target_tempo"
        const val query_target_valence = "target_valence"
        //endregion

        const val path_type = "type"
        const val path_id = "id"
        const val playlist_id = "playlist_id"
        const val user_id = "user_id"
    }

    @GET("me")
    suspend fun fetchUser(): User

    @GET("search")
    suspend fun fetchTrack(
        @Query(query_q) q: String,
        @Query(query_type) type: String = "track",
        @Query(query_limit) limit: Int = 50
    ): TracksObject

    @GET("me/player/recently-played")
    suspend fun fetchRecentlyPlayed(
        @Query(query_type) type: String = "track",
        @Query(query_limit) limit: Int = 50
    ): HistoryObject

    @GET("audio-features/{id}")
    suspend fun fetchTrackFeatures(
        @Path(path_id) id: String
    ): FeaturesObject

    @GET("search")
    suspend fun fetchArtists(
        @Query(query_q) q: String,
        @Query(query_type) type: String = "artist",
        @Query(query_limit) limit: Int = 50
    ): ArtistsResponse

    @PUT("me/player/play")
    suspend fun play(
        @Body playBody: PlayBody
    )

    @PUT("me/player/pause")
    suspend fun pause()

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
        @Query(query_limit) limit: Int? = 50
    ): TopArtist

    @GET("me/top/{type}")
    suspend fun fetchTopTracks(
        @Path(path_type) type: String? = "tracks",
        @Query(query_time_range) timeRange: String?,
        @Query(query_limit) limit: Int? = 50
    ): TopTrack

    @GET("me/following")
    suspend fun fetchFollowedArtists(
        @Query(query_type) type: String = "artist",
        @Query(query_limit) limit: Int = 50
    ): ArtistsResponse

    @GET("users/{id}/playlists")
    suspend fun fetchPlaylists(
        @Path(path_id) id: String
    ): PlaylistObject

    @POST("users/{user_id}/playlists")
    suspend fun createPlaylist(
        @Body createPlaylistBody: CreatePlaylistBody,
        @Path(user_id) id: String
    )

    @GET("playlists/{id}/tracks")
    suspend fun fetchPlaylistTracks(
        @Path(path_id) id: String,
        @Query(query_offset) offset: Int
    ): PlaylistTracksObject

    @HTTP(method = "DELETE", path = "playlists/{id}/tracks", hasBody = true)
    suspend fun removeTrackFromPlaylist(
        @Path(path_id) id: String,
        @Body removeTracksBody: RemoveTracksBody
    )

    @POST("playlists/{playlist_id}/tracks")
    suspend fun addTrackToPlaylist(
        @Path(playlist_id) id: String,
        @Query(query_uris) uris: String
    )

    @GET("recommendations")
    suspend fun fetchRecommendations(
        @Query(query_seed_tracks) seedTrackId: String,
        @Query(query_target_acousticness) acousticness: Float,
        @Query(query_target_danceability) danceability: Float,
        @Query(query_target_energy) energy: Float,
        @Query(query_target_instrumentalness) instrumentalness: Float,
        @Query(query_target_liveness) liveness: Float,
        @Query(query_target_speechiness) speechiness: Float,
        @Query(query_target_tempo) tempo: Float,
        @Query(query_target_valence) valence: Float
    ): RecommendationsObject
}
