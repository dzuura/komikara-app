package com.dza.retrofit.model

import com.google.gson.annotations.SerializedName

data class Users(

    @SerializedName("_id")
    val id: String? = null,

    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)
