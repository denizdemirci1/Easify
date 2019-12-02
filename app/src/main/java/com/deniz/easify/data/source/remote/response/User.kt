package com.deniz.easify.data.source.remote.response

import com.google.gson.annotations.SerializedName

/**
 * @User: deniz.demirci
 * @Date: 2019-11-11
 */

data class User(
    @SerializedName("country")
    val country: String,
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrl,
    @SerializedName("followers")
    val followers: Follower,
    @SerializedName("href")
    val href: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("images")
    val images: ArrayList<Image>,
    @SerializedName("product")
    val product: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("uri")
    val uri: String
) {

    data class ExternalUrl(
        @SerializedName("spotify")
        val spotify: String
    )

    data class Follower(
        @SerializedName("total")
        val total: Int
    )


}
