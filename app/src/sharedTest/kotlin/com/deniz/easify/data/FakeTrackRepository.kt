package com.deniz.easify.data

import com.deniz.easify.data.source.remote.response.FeaturesObject
import com.deniz.easify.data.source.remote.response.Track
import com.deniz.easify.data.source.remote.response.Tracks
import com.deniz.easify.data.source.remote.response.TracksObject
import com.deniz.easify.data.source.repositories.TrackRepository
import io.mockk.mockk

/**
 * @User: deniz.demirci
 * @Date: 2020-02-20
 */

class FakeTrackRepository : TrackRepository {

    override suspend fun fetchTrack(q: String): Result<TracksObject> {
        val track1 = mockk<Track>()
        val track2 = mockk<Track>()
        val tracksObject = TracksObject(
            tracks = Tracks(
                items = arrayListOf(track1, track2),
                limit = 2,
                next = null,
                offset = 0,
                previous = null,
                total = 2)
        )
        return Result.Success(tracksObject)
    }

    override suspend fun fetchTrackFeatures(id: String): Result<FeaturesObject> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}