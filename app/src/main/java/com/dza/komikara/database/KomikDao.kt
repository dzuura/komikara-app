package com.dza.komikara.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface KomikDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(komiks: KomikData)

    @Update
    suspend fun update(komiks: KomikData)

    @Query("DELETE FROM komik WHERE title = :title")
    suspend fun deleteByName(title: String)

    @Query("SELECT EXISTS(SELECT 1 FROM komik WHERE title = :title)")
    suspend fun isFavorite(title: String): Boolean

    @Query("SELECT * FROM komik")
    fun getAllkomik(): List<KomikData>

    @Query("SELECT * FROM komik WHERE id = :id")
    suspend fun getkomikById(id: Int): KomikData?

    @Query("SELECT * FROM komik WHERE title = :title")
    fun getkomikByTitle(title: String): KomikData?
}