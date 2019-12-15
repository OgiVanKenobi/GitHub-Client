package com.example.smallpdftest.network

import com.example.smallpdftest.model.*
import com.example.smallpdftest.model.repository.CommitParent
import com.example.smallpdftest.model.repository.Repository
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

    @GET("users/{user}/repos")
    fun getAllRepos(@Path("user") user: String) : Call<List<Repository>>

    @GET("repos/{owner}/{repo}/commits")
    fun getCommit(@Path("owner") owner: String, @Path("repo") repo : String) : Call<List<CommitParent>>
}