package com.example.smallpdftest.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.smallpdftest.R
import com.example.smallpdftest.adapter.CommitsAdapter
import com.example.smallpdftest.model.repository.CommitParent
import com.example.smallpdftest.util.TextUtils
import com.example.smallpdftest.viewmodel.CommitsViewModel
import kotlinx.android.synthetic.main.activity_commits.*
import kotlinx.android.synthetic.main.activity_repositories.emptyView
import kotlinx.android.synthetic.main.layout_title_toolbar.*

class CommitsActivity : AppCompatActivity() {

    private lateinit var viewModel: CommitsViewModel
    private var commitsAdapter: CommitsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_commits)
        handleIntent()
    }

    private fun handleIntent() {
        val bundle: Bundle? = intent.extras
        val commits: List<CommitParent> = bundle?.get(COMMITS_LIST_KEY) as List<CommitParent>

        initializeViewModel(commits)
        TextUtils.setTextToTextView(this, toolbar_title_text_view, getString(R.string.commits_toolbar_title_text))

        if (commits.isNotEmpty()) {
            setupRecyclerView(commits)
        } else {
            showEmptyView()
        }
    }

    private fun setupRecyclerView(commits: List<CommitParent>) {
        (commitsRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        commitsRecyclerView.layoutManager = LinearLayoutManager(this)
        commitsRecyclerView.setHasFixedSize(false)
        commitsAdapter = CommitsAdapter(this, commits)
        commitsRecyclerView.adapter = commitsAdapter
    }

    private fun showEmptyView() {
        commitsRecyclerView.visibility = View.GONE
        emptyView.visibility = View.VISIBLE
    }

    private fun initializeViewModel(commits: List<CommitParent>) {
        viewModel = ViewModelProviders.of(this).get(CommitsViewModel::class.java)
        viewModel.initialize(commits)
    }

    companion object {
        private const val COMMITS_LIST_KEY = "commits_list_key"

        /**
         * Returns intent for starting Commits activity
         *
         * @param context context
         * @param commits list of repositories commits
         */
        fun getIntent(context: Context, commits: List<CommitParent>): Intent {
            val intent = Intent(context, CommitsActivity::class.java)
            val list: ArrayList<CommitParent> = commits as ArrayList<CommitParent>
            intent.putExtra(COMMITS_LIST_KEY, list)

            return intent
        }
    }
}
