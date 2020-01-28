package com.deniz.easify.data.source.remote.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @User: deniz.demirci
 * @Date: 2020-01-28
 */

data class CreatePlaylistBody(
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String
) : Serializable