package com.example.smallpdftest.util

import android.content.Context
import com.example.smallpdftest.R

/**
 * Util class for operations including shared preferences
 */
class SharedPreferencesUtil {

    companion object {
        private const val AUTH_TOKEN_KEY = "auth_token_key"

        /**
         * Retrieves auth token from shared preferences
         */
        fun getAuthToken(context: Context) : String {
            val sharedPreference = context.getSharedPreferences(
                context.getString(R.string.app_name),
                Context.MODE_PRIVATE
            )
            return sharedPreference.getString(AUTH_TOKEN_KEY, "").toString()
        }

        /**
         * Stores access token into the shared preferences
         *
         * @param context    application context
         *@param accessToken access token to be stored
         */
        fun storeAccessToken(context: Context, accessToken: String) {
            val sharedPreference = context.getSharedPreferences(
                context.resources.getString(R.string.app_name),
                Context.MODE_PRIVATE
            )
            val editor = sharedPreference.edit()
            editor.putString(AUTH_TOKEN_KEY, accessToken)
            editor.apply()
        }
    }
}