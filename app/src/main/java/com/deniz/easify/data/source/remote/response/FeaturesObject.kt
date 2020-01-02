package com.deniz.easify.data.source.remote.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @User: deniz.demirci
 * @Date: 2019-12-31
 */

data class FeaturesObject(
    @SerializedName("danceability")
    val danceability: Float,
    @SerializedName("energy")
    val energy: Float,
    @SerializedName("key")
    val key: Int,
    @SerializedName("loudness")
    val loudness: Float,
    @SerializedName("mode")
    val mode: Int,
    @SerializedName("speechiness")
    val speechiness: Float,
    @SerializedName("acousticness")
    val acousticness: Float,
    @SerializedName("instrumentalness")
    val instrumentalness: Float,
    @SerializedName("liveness")
    val liveness: Float,
    @SerializedName("valence")
    val valence: Float,
    @SerializedName("tempo")
    val tempo: Float,
    @SerializedName("type")
    val type: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("uri")
    val uri: String,
    @SerializedName("duration_ms")
    val duration_ms: Int,
    @SerializedName("time_signature")
    val time_signature: Int
) : Serializable
