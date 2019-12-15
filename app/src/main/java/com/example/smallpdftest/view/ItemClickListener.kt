package com.example.smallpdftest.view

/**
 * Listens to the click events on list of repositories
 */
interface ItemClickListener<T> {

    /**
     * Called when an item in a list gets clicked
     *
     * @param item item that has been clicked
     */
    fun onItemClicked(item : T)
}