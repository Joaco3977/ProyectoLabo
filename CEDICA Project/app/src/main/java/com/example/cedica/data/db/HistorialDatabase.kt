package com.example.cedica.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cedica.data.dao.HistorialDao
import com.example.cedica.data.entities.EntradaHistorial

@Database(entities = [EntradaHistorial::class], version = 1, exportSchema = false)
abstract class HistorialDatabase : RoomDatabase() {
    abstract fun historialDao(): HistorialDao

    companion object {
        @Volatile
        private var INSTANCE: HistorialDatabase? = null

        fun getDatabase(context: Context): HistorialDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistorialDatabase::class.java,
                    "cedica_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
