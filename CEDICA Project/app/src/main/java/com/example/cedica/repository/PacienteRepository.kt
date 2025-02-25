package com.example.cedica.repository

import com.example.cedica.data.dao.PacienteDao
import com.example.cedica.data.entities.Paciente
import kotlinx.coroutines.flow.Flow

class PacienteRepository(private val pacienteDao: PacienteDao) {

    fun getAllPacientes(): Flow<List<Paciente>> = pacienteDao.getAllPacientes()

    suspend fun getPacienteById(pacienteId: Long): Paciente? {
        return pacienteDao.getPacienteById(pacienteId)
    }

    suspend fun insert(paciente: Paciente): Long {
        return pacienteDao.insert(paciente)
    }

    suspend fun update(paciente: Paciente) {
        pacienteDao.update(paciente)
    }

    suspend fun delete(paciente: Paciente) {
        pacienteDao.delete(paciente)
    }
}
