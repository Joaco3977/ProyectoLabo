package com.example.cedica.data.dao

import androidx.room.*
import com.example.cedica.data.entities.Paciente
import kotlinx.coroutines.flow.Flow

@Dao
interface PacienteDao {
    @Query("SELECT * FROM pacientes")
    fun getAllPacientes(): Flow<List<Paciente>>

    @Query("SELECT * FROM pacientes WHERE id = :pacienteId")
    suspend fun getPacienteById(pacienteId: Long): Paciente?  // 🔥 Agregado

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(paciente: Paciente): Long  // 🔥 Devuelve el ID generado

    @Update
    suspend fun update(paciente: Paciente)  // 🔥 Agregado

    @Delete
    suspend fun delete(paciente: Paciente)  // 🔥 Agregado
}
