package com.example.smallpdftest.model.repository

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Model class for commits
 */
data class Commit (
    @SerializedName("author") val author : CommitAuthor,
    @SerializedName("committer") val committer : CommitCommitter,
    @SerializedName("message") val message : String,
    @SerializedName("tree") val tree : Tree,
    @SerializedName("url") val url : String,
    @SerializedName("comment_count") val comment_count : Int,
    @SerializedName("verification") val verification : Verification
) : Serializable
