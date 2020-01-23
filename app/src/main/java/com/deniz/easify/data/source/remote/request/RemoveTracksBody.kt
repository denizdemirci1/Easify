package com.deniz.easify.data.source.remote.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @User: deniz.demirci
 * @Date: 2020-01-23
 */

data class RemoveTracksBody(
    @SerializedName("tracks")
    val tracks: List<Uri>
) : Serializable

data class Uri(
    @SerializedName("uri")
    val uri: String
) : Serializable