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
        const val IS_READY_TO_RATE = "easify.isReadyToRate"
        const val IS_RATED_BEFORE = "easify.isRatedBefore"
        const val DID_USER_SEE_RATING = "easify.didUserSeeRating"
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

    var isReadyToRate: Boolean
        get() =
            if (authPrefs.contains(IS_READY_TO_RATE) && !isRatedBefore)
                authPrefs.getBoolean(IS_READY_TO_RATE, false)
            else
                false
        set(value) = authPrefs.edit().putBoolean(IS_READY_TO_RATE, value).apply()

    var isRatedBefore: Boolean
        get() =
            if (authPrefs.contains(IS_RATED_BEFORE))
                authPrefs.getBoolean(IS_RATED_BEFORE, false)
            else
                false
        set(value) = authPrefs.edit().putBoolean(IS_RATED_BEFORE, value).apply()

    var didUserSeeRating: Boolean
        get() =
            if (authPrefs.contains(DID_USER_SEE_RATING))
                authPrefs.getBoolean(DID_USER_SEE_RATING, false)
            else
                false
        set(value) = authPrefs.edit().putBoolean(DID_USER_SEE_RATING, value).apply()
}
