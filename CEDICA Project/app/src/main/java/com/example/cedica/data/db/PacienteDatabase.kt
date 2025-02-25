package com.example.cedica.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cedica.data.dao.PacienteDao
import com.example.cedica.data.entities.Paciente

@Database(entities = [Paciente::class], version = 4, exportSchema = false)
abstract class PacienteDatabase : RoomDatabase() {
    abstract fun pacienteDao(): PacienteDao

    companion object {
        @Volatile
        private var INSTANCE: PacienteDatabase? = null

        fun getDatabase(context: Context): PacienteDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    PacienteDatabase::class.java,
                    "paciente_database"
                ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
            }
        }
    }
}
