package com.example.cedica.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.cedica.data.entities.EntradaHistorial

@Dao
interface HistorialDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEntrada(entrada: EntradaHistorial)
}
