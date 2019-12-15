package com.example.smallpdftest.viewmodel

import androidx.lifecycle.ViewModel
import com.example.smallpdftest.model.Repository

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
}
