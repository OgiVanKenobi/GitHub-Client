package com.example.smallpdftest.model.repository

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Verification (
    @SerializedName("verified") val verified : Boolean,
    @SerializedName("reason") val reason : String,
    @SerializedName("signature") val signature : String,
    @SerializedName("payload") val payload : String
) : Serializable