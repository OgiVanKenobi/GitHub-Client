package com.example.smallpdftest.model.repository

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Model class for commmitters nested inside commits
 */
data class CommitCommitter (
    @SerializedName("name") val name : String,
    @SerializedName("email") val email : String,
    @SerializedName("date") val date : String
    ): Serializable