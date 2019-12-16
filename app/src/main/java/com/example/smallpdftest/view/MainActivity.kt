package com.example.smallpdftest.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.smallpdftest.R
import com.example.smallpdftest.network.NetworkUrl
import com.example.smallpdftest.network.RetrofitClient
import com.example.smallpdftest.util.SharedPreferencesUtil
import com.example.smallpdftest.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private var authCode = ""
    private val authApi = NetworkUrl.AuthAPi
    private var authToken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        authToken = SharedPreferencesUtil.getAuthToken(this)
        if (authToken.isEmpty()) {
            loginButton.visibility = View.VISIBLE
        } else {
            viewModel.loadUser(this, authToken)
        }

        loginButton.setOnClickListener {
            val authIntent = Intent(
                Intent.ACTION_VIEW, Uri.parse(
                    RetrofitClient.AUTH_BASE_URL + authApi.API_AUTHORIZE
                            + authApi.CLIENT_ID_PARAM + authApi.CLIENT_ID
                            + authApi.REDIRECT_URI_PARAM + authApi.REDIRECT_URI
                )
            )
            startActivity(authIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        val uri: Uri? = intent.data
        if (uri != null && uri.toString().startsWith(authApi.REDIRECT_URI)) {
            authCode = uri.getQueryParameter("code").toString()

            val authToken = SharedPreferencesUtil.getAuthToken(this)
            if (authToken.isNullOrEmpty()) {
                viewModel.getAccessToken(this, authCode)
            } else {
                viewModel.loadUser(this, authToken)
            }
        }
    }
}
