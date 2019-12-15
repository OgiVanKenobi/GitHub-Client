package com.example.smallpdftest.model.repository

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Model class for parents
 */
data class Parents (
    @SerializedName("sha") val sha : String,
    @SerializedName("url") val url : String,
    @SerializedName("html_url") val html_url : String
) : Serializable
