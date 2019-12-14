package com.example.smallpdftest.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Object that initializes Retrofit client
 */
object RetrofitClient {
    const val AUTH_BASE_URL = "https://github.com"
    private const val BASE_URL = "https://api.github.com"
    private var retrofit: Retrofit? = null

    /**
     * Singleton method returning Retrofit instance
     */
    fun getInstance(authToken: String): GitHubApi? {
        if (retrofit == null) {
            var httpClient = OkHttpClient.Builder().addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $authToken")
                    .build()
                chain.proceed(newRequest)
            }.build()

            val gson = GsonBuilder().setLenient().create()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build()
        }
        return retrofit?.create(GitHubApi::class.java)
    }
}