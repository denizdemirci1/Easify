package com.deniz.easify.data.source.remote.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @User: deniz.demirci
 * @Date: 2020-01-23
 */

data class PlaylistObject(
    @SerializedName("items")
    val playlists: ArrayList<Playlist>,
    @SerializedName("total")
    val total: Int
) : Serializable

data class Playlist(
    @SerializedName("collaborative")
    val collaborative: Boolean,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("images")
    val images: ArrayList<Image>,
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("uri")
    val uri: String
) : Serializable

data class Owner(
    @SerializedName("id")
    val id: String
) : Serializable
