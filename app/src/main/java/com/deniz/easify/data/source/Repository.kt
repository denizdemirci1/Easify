package com.deniz.easify.data.source

import com.deniz.easify.data.Result
import com.deniz.easify.data.source.remote.request.PlayBody
import com.deniz.easify.data.source.remote.response.Tracks
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.data.source.remote.response.User

/**
 * @User: deniz.demirci
 * @Date: 2019-11-27
 */

interface Repository {

    suspend fun fetchUser(): Result<User>?

    suspend fun fetchTrack(q: String): Result<Tracks>

    suspend fun fetchTop(type: String, timeRange: String?, limit: Int?): Result<Track>

    suspend fun play(playBody: PlayBody)

}