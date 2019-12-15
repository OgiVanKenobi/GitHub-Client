package com.example.smallpdftest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.smallpdftest.R
import com.example.smallpdftest.model.repository.Repository
import com.example.smallpdftest.view.ItemClickListener

/**
 * Adapter class for list of repositories
 *
 * @param context       context
 * @param repositories  list of repositories
 * @param clickListener click listener
 */
class RepositoriesAdapter(
    private val context: Context,
    private val repositories: List<Repository>,
    private val clickListener: ItemClickListener<Repository>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RepositoriesViewHolder(LayoutInflater.from(context).inflate(R.layout.item_repository, parent, false))
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RepositoriesViewHolder) {
            holder.bind(position)
        }
    }

    /**
     * ViewHolder class used for repositories
     */
    inner class RepositoriesViewHolder internal constructor(item: View) : RecyclerView.ViewHolder(item) {
        internal var name: TextView? = item.findViewById(R.id.repositoryNameTextView)
        private var openIssues: TextView? = item.findViewById(R.id.openIssuesTextView)
        private var itemRepositoryParentLayout: ConstraintLayout? = item.findViewById(R.id.itemRepositoryParentLayout)

        /**
         * Binds data to views
         *
         * @param position position of clicked item
         */
        fun bind(position: Int) {
            val repository = repositories[position]
            name?.text = repository.name
            openIssues?.text = repository.open_issues_count.toString()
            itemRepositoryParentLayout?.setOnClickListener {
                clickListener.onItemClicked(repository)
            }
        }
    }
}
