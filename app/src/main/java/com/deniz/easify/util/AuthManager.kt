package com.deniz.easify.util

import android.content.Context
import android.content.SharedPreferences
import com.deniz.easify.data.source.remote.response.User
import com.google.gson.Gson

/**
 * @User: deniz.demirci
 * @Date: 2019-11-11
 */

class AuthManager(context: Context) {

    companion object {
        const val AUTH = "easify.auth"
        const val TOKEN = "easify.token"
        const val USER = "easify.user"
    }

    private val authPrefs: SharedPreferences =
        context.getSharedPreferences(AUTH, Context.MODE_PRIVATE)

    private val gson: Gson = Gson()

    var token: String?
        get() = if (authPrefs.contains(TOKEN)) authPrefs.getString(TOKEN, "") else null
        set(value) = authPrefs.edit().putString(TOKEN, value).apply()

    var user: User?
        get() =
            if (authPrefs.contains(USER))
                gson.fromJson(authPrefs.getString(USER, "{}"), User::class.java)
            else
                null
        set(value) = authPrefs.edit().putString(USER, gson.toJson(value)).apply()
}
