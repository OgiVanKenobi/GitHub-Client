package com.example.smallpdftest.model.repository

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Model class for commits
 */
data class CommitParent (
    @SerializedName("sha") val sha : String,
    @SerializedName("node_id") val node_id : String,
    @SerializedName("commit") val commit : Commit,
    @SerializedName("url") val url : String,
    @SerializedName("html_url") val html_url : String,
    @SerializedName("comments_url") val comments_url : String,
    @SerializedName("author") val author : Author,
    @SerializedName("committer") val committer : Committer,
    @SerializedName("message") val message : String,
    @SerializedName("parents") val parents : List<Parents>,
    @SerializedName("collapsed") var collapsed : Boolean = false
) : Serializable