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
        const val TOKEN_REFRESHED = "easify.tokenRefreshed"
        const val USER = "easify.user"
    }

    private val authPrefs: SharedPreferences =
        context.getSharedPreferences(AUTH, Context.MODE_PRIVATE)

    private val gson: Gson = Gson()

    var token: String?
        get() = if (authPrefs.contains(TOKEN)) authPrefs.getString(TOKEN, "") else null
        set(value) = authPrefs.edit().putString(TOKEN, value).apply()

    /**
     * User token provided by Spotify is valid for 1 hour.
     * tokenRefreshed is used to decide if we should try to authenticate user once again,
     * or we should show authentication failed error to the user.
     */
    var tokenRefreshed: Boolean
        get() =
            if (authPrefs.contains(TOKEN_REFRESHED))
                authPrefs.getBoolean(TOKEN_REFRESHED, false)
            else
                false
        set(value) = authPrefs.edit().putBoolean(TOKEN_REFRESHED, value).apply()

    var user: User?
        get() =
            if (authPrefs.contains(USER))
                gson.fromJson(authPrefs.getString(USER, "{}"), User::class.java)
            else
                null
        set(value) = authPrefs.edit().putString(USER, gson.toJson(value)).apply()
}
