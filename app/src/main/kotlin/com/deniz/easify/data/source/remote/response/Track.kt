package com.deniz.easify.data.source.remote.response

import com.deniz.easify.util.OpenForTesting
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @User: deniz.demirci
 * @Date: 2019-11-27
 */

@OpenForTesting
data class Track(
    @SerializedName("album")
    val album: Album,
    @SerializedName("artists")
    val artists: ArrayList<Artist>,
    @SerializedName("duration_ms")
    val duration: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("popularity")
    val popularity: Int,
    @SerializedName("uri")
    val uri: String
) : Serializable
