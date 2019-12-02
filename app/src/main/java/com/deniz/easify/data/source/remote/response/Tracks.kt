package com.deniz.easify.data.source.remote.response

import com.google.gson.annotations.SerializedName

/**
 * @User: deniz.demirci
 * @Date: 2019-11-25
 */

data class Tracks(
    @SerializedName("tracks")
    val tracks: Track
) {

    data class Track(
        @SerializedName("href")
        val href: String,
        @SerializedName("items")
        val items: ArrayList<Item>,
        @SerializedName("limit")
        val limit: Int,
        @SerializedName("next")
        val next: String?,
        @SerializedName("offset")
        val offset: Int,
        @SerializedName("previous")
        val previous: String?,
        @SerializedName("total")
        val total: Int
    )

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

    data class Artist(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("uri")
        val uri: String
    )
}