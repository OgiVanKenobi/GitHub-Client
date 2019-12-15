package com.example.smallpdftest.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.example.smallpdftest.model.repository.Repository
import com.example.smallpdftest.model.User
import com.example.smallpdftest.network.RetrofitClient
import com.example.smallpdftest.view.RepositoryActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * View model for User activity
 */
class UserViewModel : ViewModel() {

    private var user: User? = null

    /**
     * Initializes view model
     *
     * @param user user
     */
    fun initialize(user: User) {
        this.user = user
    }

    /**
     * Loads users repositories and opens new screen to show data
     */
    fun loadRepositories(context: Context, autToken: String?) {
        autToken?.let {
            user?.login?.let {
                RetrofitClient.getInstance(it)?.getAllRepos(user!!.login)?.enqueue(
                    object : Callback<List<Repository>> {
                        override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                            Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(call: Call<List<Repository>>, response: Response<List<Repository>>) {
                            val repositories = response.body()
                            if (user != null) {
                                repositories?.let { it1 -> showRepositories(context, it1) }
                            }
                        }
                    })
            }
        }
    }

    private fun showRepositories(context: Context, repositories : List<Repository>) {
        val repositoryActivityIntent = user?.let { RepositoryActivity.getIntent(context, repositories, it) }
        repositoryActivityIntent?.let { startActivity(context, it, null) }
    }
}