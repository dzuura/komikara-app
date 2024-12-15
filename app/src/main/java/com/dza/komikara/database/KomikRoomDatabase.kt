package com.dza.komikara.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dza.komikara.model.Komiks

@Database(entities = [KomikData::class], version = 1, exportSchema = false)
abstract class KomikRoomDatabase : RoomDatabase() {
    abstract fun komiksDao(): KomikDao?

    companion object {
        @Volatile
        private var INSTANCE: KomikRoomDatabase? = null

        fun getDatabase(context: Context): KomikRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KomikRoomDatabase::class.java,
                    "Komik_Database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}