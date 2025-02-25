package com.example.cedica.data.dao

import androidx.room.*
import com.example.cedica.data.entities.Terapeuta
import kotlinx.coroutines.flow.Flow

@Dao
interface TerapeutaDao {
    @Query("SELECT * FROM terapeutas")
    fun getAllTerapeutas(): Flow<List<Terapeuta>>

    @Query("SELECT * FROM terapeutas WHERE nombre = :nombre")
    suspend fun getTerapeutaByName(nombre: String): Terapeuta?  // 🔥 Agregado

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(terapeuta: Terapeuta): Long  // 🔥 Devuelve el ID generado

    @Update
    suspend fun update(terapeuta: Terapeuta)  // 🔥 Agregado

    @Delete
    suspend fun delete(terapeuta: Terapeuta)  // 🔥 Agregado

    @Query("SELECT * FROM terapeutas WHERE id = :terapeutaId")
    suspend fun getTerapeutaById(terapeutaId: Long?): Terapeuta?
}
