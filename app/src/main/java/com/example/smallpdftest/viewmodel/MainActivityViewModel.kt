package com.example.smallpdftest.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.example.smallpdftest.R
import com.example.smallpdftest.model.AccessToken
import com.example.smallpdftest.model.User
import com.example.smallpdftest.network.GitHubApi
import com.example.smallpdftest.network.RetrofitClient
import com.example.smallpdftest.util.Consumer
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
    private val authTokenKey = "authToken"

    /**
     * Method for fetching the access token from the API
     *
     * @param context context
     * @param authCode authentication code
     * @param isTokenFetched is token fetched listener
     */
    fun getAccessToken(context: Context, authCode: String, isTokenFetched: Consumer<Boolean>) {
        val retrofit = Retrofit.Builder()
            .baseUrl(RetrofitClient.AUTH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(GitHubApi::class.java).getAccessToken(clientID, clientSecret, authCode)
            .enqueue(object : Callback<AccessToken> {
                override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                    isTokenFetched.consume(false)
                }

                override fun onResponse(call: Call<AccessToken>, response: Response<AccessToken>) {
                    val accessToken = response.body()?.accessToken
                    accessToken?.let { storeAccessToken(context, it) }
//                    isTokenFetched.consume(true)

                    if (accessToken != null) {
                        loadUser(context, accessToken)
                    }
                }
            })
    }

    /**
     * Method
     */
    private fun loadUser(context: Context, accessToken: String) {
        RetrofitClient.getInstance(accessToken)?.getUser()?.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                val user = response.body()
                if (user != null) {
                    val userActivityIntent = UserActivity.getIntent(context, user)
                    startActivity(context, userActivityIntent, null)
                }
            }
        })
    }

    /**
     * Stores access token into the shared preferences
     *
     * @param context    application context
     *@param accessToken access token to be stored
     */
    private fun storeAccessToken(context: Context, accessToken: String) {
        val sharedPreference = context.getSharedPreferences(
            context.resources.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        val editor = sharedPreference.edit()
        editor.putString(authTokenKey, accessToken)
        editor.apply()
    }
}