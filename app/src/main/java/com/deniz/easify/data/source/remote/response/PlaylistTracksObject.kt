package com.deniz.easify.data.source.remote.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @User: deniz.demirci
 * @Date: 2020-01-23
 */

data class PlaylistTracksObject(
    @SerializedName("items")
    val playlistTracks: ArrayList<PlaylistTracks>,
    @SerializedName("total")
    val total: Int
) : Serializable

data class PlaylistTracks(
    @SerializedName("track")
    val track: Track
) : Serializable
