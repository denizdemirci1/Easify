package com.deniz.easify.data.source.remote.response

import com.google.gson.annotations.SerializedName

/**
 * @User: deniz.demirci
 * @Date: 2019-12-02
 */

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
)
