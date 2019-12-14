package com.example.smallpdftest.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    const val AUTH_BASE_URL = "https://api.github.com"
    const val BASE_URL = "https://github.com"

    val instance : GitHubApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(AUTH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return@lazy retrofit.create(GitHubApi::class.java)
    }
}