package com.dza.komikara.model

import com.google.gson.annotations.SerializedName

data class Komiks (

    @SerializedName("_id")
    val id: String? = null,

    @SerializedName("title")
    val title: String,

    @SerializedName("author")
    val author: String,

    @SerializedName("genre")
    val genre: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("released")
    val released: String,

    @SerializedName("synopsis")
    val synopsis: String,

    @SerializedName("cover")
    val cover: String
) {
    fun getShortTitle(): String {
        return if (title.length > 20) title.substring(0, 20) + "..." else title
    }
}