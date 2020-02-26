package com.deniz.easify.data

import com.deniz.easify.data.source.remote.response.FeaturesObject
import com.deniz.easify.data.source.remote.response.TracksObject
import com.deniz.easify.data.source.repositories.TrackRepository
import java.lang.Exception
import org.mockito.Mockito

/**
 * @User: deniz.demirci
 * @Date: 2020-02-20
 */

class FakeTrackRepository : TrackRepository {

    var shouldReturnError = false

    override suspend fun fetchTrack(q: String): Result<TracksObject> {
        if (shouldReturnError) {
            return Result.Error(Exception("fetchTrack() failed"))
        }
        return Result.Success(Mockito.mock(TracksObject::class.java))
    }

    override suspend fun fetchTrackFeatures(id: String): Result<FeaturesObject> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }
}
