package com.example.smallpdftest.network

import com.example.smallpdftest.model.AccessToken
import com.example.smallpdftest.model.User
import retrofit2.Call
import retrofit2.http.*

/**
 * Interface containing GitHub Api methods
 */
interface GitHubApi {

    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") accessCode: String
    ): Call<AccessToken>

    @GET("user")
    fun getUser(): Call<User>
}