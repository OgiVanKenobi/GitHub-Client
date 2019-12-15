package com.example.smallpdftest.model.repository

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Tree (
    @SerializedName("sha") val sha : String,
    @SerializedName("url") val url : String
) : Serializable