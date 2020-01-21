package com.deniz.easify.data

import com.deniz.easify.data.source.Repository
import com.deniz.easify.data.source.remote.request.PlayBody
import com.deniz.easify.data.Result.Success
import com.deniz.easify.data.source.remote.response.*
import io.mockk.mockk

/**
 * @User: deniz.demirci
 * @Date: 2020-01-10
 */

class FakeRepository : Repository {

    override suspend fun fetchUser(): Result<User>? {
        return Success(User(
                "TR",
                "Deniz Demirci",
                "d.demirci93@gmail.com",
                User.ExternalUrl("https://open.spotify.com/user/11131803020"),
                User.Follower(37),
                "11131803020",
                arrayListOf(),
                "premium",
                "user",
                "spotify:user:11131803020"
                ))
    }

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
        return Success(tracksObject)
    }

    override suspend fun fetchTrackFeatures(id: String): Result<FeaturesObject> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun fetchArtists(q: String): Result<ArtistsResponse> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun fetchTopArtists(
        type: String,
        timeRange: String?,
        limit: Int?
    ): Result<TopArtist> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun fetchTopTracks(
        type: String,
        timeRange: String?,
        limit: Int?
    ): Result<TopTrack> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun fetchFollowedArtists(): Result<ArtistsResponse> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun play(playBody: PlayBody) {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun pause() {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun followArtist(id: String) {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun unfollowArtist(id: String) {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun fetchRecommendations(
        danceability: Float,
        energy: Float,
        speechiness: Float,
        acousticness: Float,
        instrumentalness: Float,
        liveness: Float,
        valence: Float,
        tempo: Float,
        seedTrackId: String
    ): Result<RecommendationsObject> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }
}
