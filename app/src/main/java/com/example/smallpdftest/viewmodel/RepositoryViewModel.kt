package com.example.smallpdftest.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.smallpdftest.model.repository.CommitParent
import com.example.smallpdftest.model.repository.Repository
import com.example.smallpdftest.network.RetrofitClient
import com.example.smallpdftest.view.CommitsActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * View model for Repositories activity
 */
class RepositoryViewModel : ViewModel() {

    private var repositories: List<Repository>? = null

    /**
     * Initializes view model
     *
     * @param repositories list of repositories
     */
    fun initialize(repositories: List<Repository>) {
        this.repositories = repositories
    }

    /**
     * Method that opens repository details when repository is clicked
     */
    fun onRepositoryClicked(context : Context, authToken : String?, owner: String, repo: String) {
        authToken?.let {
            RetrofitClient.getInstance(it)?.getCommit(owner, repo)?.enqueue(object : Callback<List<CommitParent>> {
                override fun onFailure(call: Call<List<CommitParent>>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<List<CommitParent>>, response: Response<List<CommitParent>>) {
                    val commits = response.body()
                    if (commits != null) {
                        commits?.let { it1 -> showCommits(context, it1) }
                    }
                }
            })
        }
    }

    private fun showCommits(context: Context, commits : List<CommitParent>) {
        val commitsIntent = CommitsActivity.getIntent(context, commits)
        context.startActivity(commitsIntent)
    }
}
