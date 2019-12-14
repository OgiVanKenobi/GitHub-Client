package com.example.smallpdftest.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.smallpdftest.viewmodel.MainActivityViewModel
import com.example.smallpdftest.R
import com.example.smallpdftest.network.NetworkUrl
import com.example.smallpdftest.network.RetrofitClient
import com.example.smallpdftest.util.Consumer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: MainActivityViewModel
    private var authCode = ""
    private val authTokenKey = "authToken"
    private val authApi = NetworkUrl.AuthAPi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        loginButton.setOnClickListener {
            val authIntent = Intent(Intent.ACTION_VIEW, Uri.parse(RetrofitClient.AUTH_BASE_URL + authApi.API_AUTHORIZE
                            + authApi.CLIENT_ID_PARAM + authApi.CLIENT_ID
                            + authApi.REDIRECT_URI_PARAM + authApi.REDIRECT_URI))
            startActivity(authIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        val uri: Uri? = intent.data
        if (uri != null && uri.toString().startsWith(authApi.REDIRECT_URI)) {
            authCode = uri.getQueryParameter("code").toString()
            viewModel.getAccessToken(this, authCode, object : Consumer<Boolean> {
                override fun consume(isTokenFetched: Boolean) {
                    if (isTokenFetched) {
                        showAuthToken()
                    }
                }
            })
        }
    }

    private fun showAuthToken() {
        val preference = getSharedPreferences(resources.getString(R.string.app_name), Context.MODE_PRIVATE)
        val authToken = preference.getString(authTokenKey, "")

        if (!authToken.isNullOrEmpty()) {
            textLabel.text = authToken
        }
    }
}
