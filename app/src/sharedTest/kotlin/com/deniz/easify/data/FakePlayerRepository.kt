package com.deniz.easify.data

import com.deniz.easify.data.source.remote.request.PlayBody
import com.deniz.easify.data.source.remote.response.HistoryObject
import com.deniz.easify.data.source.repositories.PlayerRepository
import org.mockito.Mockito

/**
 * @User: deniz.demirci
 * @Date: 2020-02-21
 */

class FakePlayerRepository : PlayerRepository {

    var shouldReturnError = false

    override suspend fun play(playBody: PlayBody) {}

    override suspend fun pause() {}

    override suspend fun fetchRecentlyPlayed(): Result<HistoryObject> {
        return if (shouldReturnError)
            Result.Error(Exception("fetchRecentlyPlayed() failed"))
        else
            Result.Success(Mockito.mock(HistoryObject::class.java))
    }
}
