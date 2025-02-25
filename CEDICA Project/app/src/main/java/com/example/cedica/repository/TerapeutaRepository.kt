package com.example.cedica.repository

import com.example.cedica.data.dao.TerapeutaDao
import com.example.cedica.data.entities.Terapeuta
import kotlinx.coroutines.flow.Flow

class TerapeutaRepository(private val terapeutaDao: TerapeutaDao) {

    fun getAllTerapeutas(): Flow<List<Terapeuta>> = terapeutaDao.getAllTerapeutas()

    suspend fun getTerapeutaByName(nombre: String): Terapeuta? {
        return terapeutaDao.getTerapeutaByName(nombre)
    }

    suspend fun insert(terapeuta: Terapeuta) {
        terapeutaDao.insert(terapeuta)
    }

    suspend fun update(terapeuta: Terapeuta) {
        terapeutaDao.update(terapeuta)
    }

    suspend fun delete(terapeuta: Terapeuta) {
        terapeutaDao.delete(terapeuta)
    }
}
