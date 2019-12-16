package com.example.smallpdftest.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.smallpdftest.R
import com.example.smallpdftest.model.User
import com.example.smallpdftest.util.TextUtils
import com.example.smallpdftest.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel
    private val authTokenKey = "authToken"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        handleIntent()

        viewRepositoriesButton.setOnClickListener {
            val sharedPreference = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
            viewModel.loadRepositories(this, sharedPreference.getString(authTokenKey, ""))
        }
    }

    private fun handleIntent() {
        val bundle : Bundle? = intent.extras
        val user : User = bundle?.getSerializable(USER_KEY) as User

        initializeViewModel(user)
        showData(user)
    }

    private fun showData(user: User) {
        Glide.with(this).load(user.avatarUrl).placeholder(R.drawable.image_placeholder).into(avatarImageView)
        TextUtils.setTextToTextView(this, nameTextView, user.name)
        TextUtils.setTextToTextView(this, companyTextView, user.company)
    }

    private fun initializeViewModel(user: User) {
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        viewModel.initialize(user)
    }

    companion object {
        private const val USER_KEY = "user_key"

        /**
         * Returns intent for starting User activity
         *
         * @param context context
         * @param user    user model
         */
        fun getIntent(context: Context, user : User): Intent {
            val intent = Intent(context, UserActivity::class.java)
            var bundle = Bundle()
            bundle.putSerializable(USER_KEY, user)
            intent.putExtras(bundle)
            return intent
        }
    }
}