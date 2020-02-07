package com.deniz.easify.data.source.remote.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @User: deniz.demirci
 * @Date: 2019-12-02
 */

data class Artist(
    @SerializedName("followers")
    val followers: Follower,
    @SerializedName("genres")
    val genres: ArrayList<String>,
    @SerializedName("id")
    val id: String,
    @SerializedName("images")
    val images: ArrayList<Image>,
    @SerializedName("name")
    val name: String,
    @SerializedName("popularity")
    val popularity: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("uri")
    val uri: String
) : Serializable {
    data class Follower(
        @SerializedName("total")
        val total: Int
    ) : Serializable
}
