package com.example.smallpdftest.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.smallpdftest.R
import com.example.smallpdftest.model.User
import com.example.smallpdftest.util.TextUtils
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        handleIntent()
    }

    private fun handleIntent() {
        val intent = intent
        val avatarURl = intent.getStringExtra(USER_AVATAR_KEY)
        val name = intent.getStringExtra(USER_NAME_KEY)
        val company = intent.getStringExtra(USER_COMPANY_KEY)

        Glide.with(this).load(avatarURl).into(avatarImageView)
        TextUtils.setTextToTextView(this, nameTextView, name)
        TextUtils.setTextToTextView(this, companyTextView, company)
    }

    companion object {
        private const val USER_AVATAR_KEY = "user_avatar_key"
        private const val USER_NAME_KEY = "user_name_key"
        private const val USER_COMPANY_KEY = "user_company_key"

        /**
         * Returns intent for starting User activity
         *
         * @param context context
         * @param user    user model
         */
        fun getIntent(context: Context, user : User): Intent {
            val intent = Intent(context, UserActivity::class.java)
            intent.putExtra(USER_AVATAR_KEY, user.avatarUrl)
            intent.putExtra(USER_NAME_KEY, user.name)
            intent.putExtra(USER_COMPANY_KEY, user.company)
            return intent
        }
    }
}