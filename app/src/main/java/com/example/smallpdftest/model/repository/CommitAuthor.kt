package com.example.smallpdftest.model.repository

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Model class for commits authors
 */
data class CommitAuthor(
    @SerializedName("name") val name : String,
    @SerializedName("email") val email : String,
    @SerializedName("date") val date : String
) : Serializable
