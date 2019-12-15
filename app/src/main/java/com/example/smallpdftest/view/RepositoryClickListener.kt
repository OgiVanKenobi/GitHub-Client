package com.example.smallpdftest.view

import com.example.smallpdftest.model.Repository

/**
 * Listens to the click events on list of repositories
 */
interface RepositoryClickListener {

    /**
     * Called when repository in list gets clicked
     *
     * @param repository repository that has been clicked
     */
    fun onRepositoryClicked(repository : Repository)
}