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
    @SerializedName("id")
    val id: String
) : Serializable
