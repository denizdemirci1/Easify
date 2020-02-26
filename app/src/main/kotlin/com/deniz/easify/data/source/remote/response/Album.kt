package com.deniz.easify.data.source.remote.response

import com.deniz.easify.util.OpenForTesting
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @User: deniz.demirci
 * @Date: 2019-12-02
 */

@OpenForTesting
data class Album(
    @SerializedName("album_type")
    val albumType: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("images")
    val images: ArrayList<Image>,
    @SerializedName("name")
    val name: String,
    @SerializedName("uri")
    val uri: String
) : Serializable
