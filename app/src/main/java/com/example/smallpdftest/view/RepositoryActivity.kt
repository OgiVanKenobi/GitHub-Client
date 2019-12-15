package com.example.smallpdftest.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.smallpdftest.R
import com.example.smallpdftest.adapter.RepositoriesAdapter
import com.example.smallpdftest.model.Repository
import com.example.smallpdftest.model.User
import com.example.smallpdftest.util.TextUtils
import com.example.smallpdftest.viewmodel.RepositoryViewModel
import kotlinx.android.synthetic.main.activity_repositories.*


class RepositoryActivity : AppCompatActivity(), RepositoryClickListener {

    private lateinit var viewModel: RepositoryViewModel
    private val userKey = "user_key"
    private var repositoriesAdapter : RepositoriesAdapter? = null
    private val authTokenKey = "authToken"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repositories)
        handleIntent()
    }

    override fun onRepositoryClicked(repository: Repository) {
        Toast.makeText(this, repository.name + " clicked!", Toast.LENGTH_SHORT).show()
    }

    private fun handleIntent() {
        val bundle: Bundle? = intent.extras
        val repositories: List<Repository> = bundle?.get(REPOSITORY_LIST_KEY) as List<Repository>
        val user : User = bundle.getSerializable(userKey) as User

        initializeViewModel(repositories)
        setupHeader(user)

        if (repositories.isNotEmpty()) {
            showData(repositories)
        } else {
            showEmptyView()
        }
    }

    private fun showData(repositories: List<Repository>) {
        repositoriesRecyclerView.layoutManager = LinearLayoutManager(this)
        repositoriesAdapter = RepositoriesAdapter(this, repositories, this)
        repositoriesRecyclerView.adapter = repositoriesAdapter
    }

    private fun showEmptyView() {
        repositoriesRecyclerView.visibility = View.GONE
        emptyView.visibility = View.VISIBLE
    }

    private fun setupHeader(user: User) {
        Glide.with(this).load(user.avatarUrl).into(avatarIcon)
        TextUtils.setTextToTextView(
            this, repositoriesHeaderTextView,
            String.format(getString(R.string.repositories_header_title, user.login))
        )
    }

    private fun initializeViewModel(repositories: List<Repository>) {
        viewModel = ViewModelProviders.of(this).get(RepositoryViewModel::class.java)
        viewModel.initialize(repositories)
    }

    companion object {
        private const val REPOSITORY_LIST_KEY = "repository_list_key"
        private const val USER_KEY = "user_key"
        /**
         * Returns intent for starting Repositories activity
         *
         * @param context context
         * @param repositories list of repositories
         */
        fun getIntent(context: Context, repositories: List<Repository>, user : User): Intent {
            val intent = Intent(context, RepositoryActivity::class.java)
            var list: ArrayList<Repository> = repositories as ArrayList<Repository>
            intent.putExtra(REPOSITORY_LIST_KEY, list)

            var bundle = Bundle()
            bundle.putSerializable(USER_KEY, user)
            intent.putExtras(bundle)

            return intent
        }
    }
}
