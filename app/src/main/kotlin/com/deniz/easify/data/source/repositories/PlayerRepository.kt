package com.deniz.easify.data.source.repositories

import androidx.annotation.VisibleForTesting
import com.deniz.easify.data.Result
import com.deniz.easify.data.source.remote.service.SpotifyService
import com.deniz.easify.data.source.remote.request.PlayBody
import com.deniz.easify.data.source.remote.response.HistoryObject
import com.deniz.easify.util.wrapEspressoIdlingResource

/**
 * @User: deniz.demirci
 * @Date: 2020-02-20
 */

interface PlayerRepository {

    suspend fun play(playBody: PlayBody)

    suspend fun pause()

    suspend fun fetchRecentlyPlayed(): Result<HistoryObject>

}

class DefaultPlayerRepository(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal val service: SpotifyService
) : PlayerRepository {

    override suspend fun play(playBody: PlayBody) {
        wrapEspressoIdlingResource {
            try {
                service.play(playBody)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun pause() {
        wrapEspressoIdlingResource {
            try {
                service.pause()
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun fetchRecentlyPlayed(): Result<HistoryObject> {
        wrapEspressoIdlingResource {
            return try {
                val history = service.fetchRecentlyPlayed()
                Result.Success(history)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }
}