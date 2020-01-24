package com.deniz.easify.data.source.remote.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @User: deniz.demirci
 * @Date: 2020-01-24
 */

data class HistoryObject(
    @SerializedName("items")
    val history: ArrayList<History>
) : Serializable

data class History(
    @SerializedName("track")
    val track: Track
)