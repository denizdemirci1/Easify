package com.deniz.easify.data.source.remote.response

import com.google.gson.annotations.SerializedName

/**
 * @User: deniz.demirci
 * @Date: 2019-11-25
 */

data class Tracks(
    @SerializedName("tracks")
    val tracks: Track
)