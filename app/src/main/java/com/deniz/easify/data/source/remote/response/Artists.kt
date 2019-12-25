package com.deniz.easify.data.source.remote.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @User: deniz.demirci
 * @Date: 2019-12-25
 */

data class Artists(
    @SerializedName("items")
    val items: ArrayList<Artist>
) : Serializable