package com.example.smallpdftest.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Model class for permissions
 */
data class Permissions (

    @SerializedName("admin") val admin : Boolean,
    @SerializedName("push") val push : Boolean,
    @SerializedName("pull") val pull : Boolean
) : Serializable