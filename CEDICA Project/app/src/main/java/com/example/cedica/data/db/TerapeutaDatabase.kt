package com.example.cedica.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cedica.data.dao.TerapeutaDao
import com.example.cedica.data.entities.Terapeuta

@Database(entities = [Terapeuta::class], version = 1, exportSchema = false)
abstract class TerapeutaDatabase : RoomDatabase() {
    abstract fun terapeutaDao(): TerapeutaDao

    companion object {
        @Volatile
        private var INSTANCE: TerapeutaDatabase? = null

        fun getDatabase(context: Context): TerapeutaDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TerapeutaDatabase::class.java,
                    "terapeuta_database"
                ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
            }
        }
    }
}
