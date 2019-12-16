package com.example.smallpdftest.viewmodel

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.example.smallpdftest.model.AccessToken
import com.example.smallpdftest.model.User
import com.example.smallpdftest.network.GitHubApi
import com.example.smallpdftest.network.RetrofitClient
import com.example.smallpdftest.util.SharedPreferencesUtil
import com.example.smallpdftest.view.UserActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * View model for MainActivity
 */
class MainActivityViewModel : ViewModel() {

    private val clientID = "3b66ee08ba62d1f0bd7c"
    private val clientSecret = "00b5239cbb90742671d8b6df736f69ed2cfb5fdc"

    /**
     * Method for fetching the access token from the API
     *
     * @param activity activity
     * @param authCode authentication code
     */
    fun getAccessToken(activity: Activity, authCode: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(RetrofitClient.AUTH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(GitHubApi::class.java).getAccessToken(clientID, clientSecret, authCode)
            .enqueue(object : Callback<AccessToken> {
                override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                }

                override fun onResponse(call: Call<AccessToken>, response: Response<AccessToken>) {
                    val accessToken = response.body()?.accessToken
                    accessToken?.let { SharedPreferencesUtil.storeAccessToken(activity, it) }

                    if (accessToken != null) {
                        loadUser(activity, accessToken)
                    }
                }
            })
    }

    /**
     * Method for fetching the user and opening User Activity
     *
     * @param activity activity
     * @param accessToken access token
     */
    fun loadUser(activity: Activity, accessToken: String) {
        RetrofitClient.getInstance(accessToken)?.getUser()?.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                val user = response.body()
                if (user != null) {
                    showUserActivity(activity, user)
                    activity.finish()
                }
            }
        })
    }

    /**
     * Opens User Activity
     *
     * @param context context
     * @param user    user to be shown on next activity
     */
    private fun showUserActivity(context: Context, user: User) {
        val userActivityIntent = UserActivity.getIntent(context, user)
        startActivity(context, userActivityIntent, null)
    }
}