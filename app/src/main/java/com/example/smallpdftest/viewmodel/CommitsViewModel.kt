package com.example.smallpdftest.viewmodel

import androidx.lifecycle.ViewModel
import com.example.smallpdftest.model.repository.CommitParent

class CommitsViewModel : ViewModel() {

    private var commits: List<CommitParent>? = null

    /**
     * Initializes view model
     */
    fun initialize(commits: List<CommitParent>) {
        this.commits = commits
    }
}
