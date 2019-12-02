package com.deniz.easify.data.source.remote.response

import com.deniz.easify.data.source.remote.response.Tracks
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @User: deniz.demirci
 * @Date: 2019-11-27
 */

data class Item(
    @SerializedName("album")
    val album: Tracks.Album,
    @SerializedName("artists")
    val artists: ArrayList<Tracks.Artist>,
    @SerializedName("available_markets")
    val availableMarkets: ArrayList<String>,
    @SerializedName("duration_ms")
    val duration: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("popularity")
    val popularity: Int,
    @SerializedName("uri")
    val uri: String
) : Serializable