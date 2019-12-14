package com.example.smallpdftest.network

/**
 * Urls used in project
 */
object NetworkUrl {

    interface AuthAPi {
        companion object {
            const val REDIRECT_URI = "smallpdftest://callback"
            const val CLIENT_ID = "3b66ee08ba62d1f0bd7c"
            const val API_AUTHORIZE = "/login/oauth/authorize"
            const val CLIENT_ID_PARAM= "?client_id="
            const val REDIRECT_URI_PARAM= "&redirect_uri="
        }
    }
}
