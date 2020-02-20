package com.deniz.easify.data.source.repositories

import androidx.annotation.VisibleForTesting
import com.deniz.easify.data.Result
import com.deniz.easify.data.source.remote.service.SpotifyService
import com.deniz.easify.data.source.remote.request.CreatePlaylistBody
import com.deniz.easify.data.source.remote.request.RemoveTracksBody
import com.deniz.easify.data.source.remote.response.PlaylistObject
import com.deniz.easify.data.source.remote.response.PlaylistTracksObject
import com.deniz.easify.util.AuthManager

/**
 * @User: deniz.demirci
 * @Date: 2020-02-20
 */

interface PlaylistRepository {

    suspend fun fetchPlaylists(id: String): Result<PlaylistObject>

    suspend fun createPlaylist(createPlaylistBody: CreatePlaylistBody)

    suspend fun fetchPlaylistTracks(id: String, offset: Int): Result<PlaylistTracksObject>

    suspend fun removeTrackFromPlaylist(id: String, removeTracksBody: RemoveTracksBody)

    suspend fun addTrackToPlaylist(id: String, uris: String)
}

class DefaultPlaylistRepository(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal val service: SpotifyService,
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal val authManager: AuthManager
) : PlaylistRepository {

    override suspend fun fetchPlaylists(id: String): Result<PlaylistObject> {
        return try {
            val playlistObject = service.fetchPlaylists(id)
            Result.Success(playlistObject)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun createPlaylist(createPlaylistBody: CreatePlaylistBody) {
        try {
            service.createPlaylist(createPlaylistBody, authManager.user!!.id)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun fetchPlaylistTracks(id: String, offset: Int): Result<PlaylistTracksObject> {
        return try {
            val playlistTracksObject = service.fetchPlaylistTracks(id, offset)
            Result.Success(playlistTracksObject)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun removeTrackFromPlaylist(id: String, removeTracksBody: RemoveTracksBody) {
        try {
            service.removeTrackFromPlaylist(id, removeTracksBody)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun addTrackToPlaylist(id: String, uris: String) {
        try {
            service.addTrackToPlaylist(id, uris)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}