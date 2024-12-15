package com.dza.komikara.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "komik")
data class KomikData (

    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int = 0,

    @ColumnInfo("title")
    val title: String,

    @ColumnInfo("author")
    val author: String,

    @ColumnInfo("genre")
    val genre: String,

    @ColumnInfo("type")
    val type: String,

    @ColumnInfo("status")
    val status: String,

    @ColumnInfo("released")
    val released: String,

    @ColumnInfo("synopsis")
    val synopsis: String,

    @ColumnInfo("cover")
    val cover: String
)