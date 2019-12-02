package com.deniz.easify.data.source.remote

import com.deniz.easify.BuildConfig
import retrofit2.HttpException
import java.net.UnknownHostException

/**
 * @User: deniz.demirci
 * @Date: 2019-11-27
 */

fun parseNetworkError(e: Exception): String {

    return when(e) {
        is HttpException -> {
            if (BuildConfig.DEBUG) {
                "Response: ${e.response()} \n Code: ${e.code()} \n Message: ${e.message()}"
            } else {
                e.response()?.errorBody()?.string() ?: "Something went wrong :("
            }
        }

        is UnknownHostException -> "It's on you, consider checking your internet connection"

        else -> {
            "Something went wrong :( and Exception type is not specified."
        }
    }
}
