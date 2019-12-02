package com.deniz.easify.data.source.remote.request

import com.google.gson.annotations.SerializedName

/**
 * @User: deniz.demirci
 * @Date: 2019-11-28
 */

data class PlayBody(
    @SerializedName("uris")
    val uri: ArrayList<String>
)