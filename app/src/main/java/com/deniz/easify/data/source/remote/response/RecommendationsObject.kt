package com.deniz.easify.data.source.remote.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @User: deniz.demirci
 * @Date: 2020-01-02
 */

data class RecommendationsObject(
    @SerializedName("tracks")
    val tracks: ArrayList<Track>
) : Serializable
