package com.example.smallpdftest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smallpdftest.R
import com.example.smallpdftest.model.repository.CommitParent
import com.example.smallpdftest.util.TextUtils


/**
 * Adapter class for list of commits
 *
 * @param context context
 * @param commits list of commits
 */
class CommitsAdapter(
    private val context: Context,
    private val commits: List<CommitParent>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CommitsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_commit, parent, false))
    }

    override fun getItemCount(): Int {
        return commits.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CommitsViewHolder) {
            holder.bind(position)
        }
    }

    /**
     * ViewHolder class used for commits
     */
    inner class CommitsViewHolder internal constructor(item: View) : RecyclerView.ViewHolder(item) {
        private var parentCommitMessage: TextView? = item.findViewById(R.id.commitParentMessageTextView)
        private var parentCommitSha: TextView? = item.findViewById(R.id.commitParentShaTextView)

        private var commitDetailsLayout: LinearLayout? = item.findViewById(R.id.commitDetailsLayout)
        private var commitMessage: TextView? = item.findViewById(R.id.commitMessageTextView)
        private var committerAvatarIcon: ImageView? = item.findViewById(R.id.committerIcon)
        private var committerName: TextView? = item.findViewById(R.id.committerNameTextVIew)
        private var authorAvatarIcon: ImageView? = item.findViewById(R.id.authorIcon)
        private var authorName: TextView? = item.findViewById(R.id.authorNameTextVIew)
        private var date: TextView? = item.findViewById(R.id.dateTextVIew)
        private var sha: TextView? = item.findViewById(R.id.shaTextVIew)
        private var url: TextView? = item.findViewById(R.id.urlTextVIew)
        private var commitParentLayout: LinearLayout? = item.findViewById(R.id.itemCommitParentLayout)

        /**
         * Binds data to views
         *
         * @param position position of clicked item
         */
        fun bind(position: Int) {
            val commitParent = commits[position]
            bindParentView(commitParent)
            bindChildViews(commitParent)
            setParentClickListener(position)
        }

        private fun bindParentView(commitParent: CommitParent) {
            parentCommitMessage?.let { TextUtils.setTextToTextView(context, it, commitParent.commit.message) }
            parentCommitSha?.let { TextUtils.setTextToTextView(context, it, commitParent.sha) }
        }

        private fun bindChildViews(commitParent: CommitParent) {
            commitMessage?.let { TextUtils.setTextToTextView(context, it, commitParent.commit.message) }
            committerAvatarIcon?.let { Glide.with(context).load(commitParent.committer.avatar_url).into(it) }
            committerName?.let { TextUtils.setTextToTextView(context, it, commitParent.commit.committer.name) }
            authorAvatarIcon?.let { Glide.with(context).load(commitParent.author.avatar_url).into(it) }
            authorName?.let { TextUtils.setTextToTextView(context, it, commitParent.commit.author.name) }
            date?.let { TextUtils.setTextToTextView(context, it, commitParent.commit.committer.date.substring(0, 10)) }
            sha?.let { TextUtils.setTextToTextView(context, it, commitParent.sha) }
            if (commitParent.parents.isNotEmpty()) {
                url?.let { TextUtils.setTextToTextView(context, it, commitParent.parents[0].html_url) }
            }
        }

        private fun setParentClickListener(position: Int) {
            commitParentLayout?.setOnClickListener {
                val collapsed = commits[position].collapsed
                if (collapsed) {
                    commitDetailsLayout?.visibility = View.GONE
                } else {
                    commitDetailsLayout?.visibility = View.VISIBLE
                }
                commits[position].collapsed = !collapsed
                notifyItemChanged(position)
            }
        }
    }
}
