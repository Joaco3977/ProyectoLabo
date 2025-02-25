package com.example.cedica.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cedica.data.db.TerapeutaDatabase
import com.example.cedica.data.dao.TerapeutaDao
import com.example.cedica.data.entities.Terapeuta
import com.example.cedica.utilities.DataStoreHelper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TerapeutaViewModel(application: Application) : AndroidViewModel(application) {

    private val terapeutaDao: TerapeutaDao =
        TerapeutaDatabase.getDatabase(application).terapeutaDao()

    private val dataStoreHelper = DataStoreHelper(application) // 🔥 Para guardar el terapeuta seleccionado

    // Lista de terapeutas observables
    private val _terapeutas = MutableStateFlow<List<Terapeuta>>(emptyList())
    val terapeutas: StateFlow<List<Terapeuta>> = _terapeutas.asStateFlow()

    // Terapeuta seleccionado observable
    private val _terapeutaSeleccionado = MutableStateFlow<Terapeuta?>(null)
    val terapeutaSeleccionado: StateFlow<Terapeuta?> = _terapeutaSeleccionado.asStateFlow()

    init {
        cargarTerapeutas()
        cargarTerapeutaSeleccionado() // 🔥 Cargar el terapeuta guardado
    }

    private fun cargarTerapeutas() {
        viewModelScope.launch {
            terapeutaDao.getAllTerapeutas().collect { lista ->
                _terapeutas.value = lista
            }
        }
    }

    private fun cargarTerapeutaSeleccionado() {
        viewModelScope.launch {
            dataStoreHelper.terapeutaSeleccionado.collect { terapeuta_id ->
                val terapeuta = terapeutaDao.getTerapeutaById(terapeuta_id)
                _terapeutaSeleccionado.value = terapeuta
            }
        }
    }

    fun selectTerapeuta(terapeuta: Terapeuta) {
        viewModelScope.launch {
            dataStoreHelper.saveSelectedTerapeuta(terapeuta.id)
            _terapeutaSeleccionado.value = terapeuta
        }
    }

    fun limpiarTerapeutaSeleccionado() {
        viewModelScope.launch {
            dataStoreHelper.clearSelectedTerapeuta()
            _terapeutaSeleccionado.value = null
        }
    }

    fun insertTerapeuta(terapeuta: Terapeuta) {
        viewModelScope.launch {
            terapeutaDao.insert(terapeuta)
        }
    }

    fun updateTerapeuta(terapeuta: Terapeuta) {
        viewModelScope.launch {
            terapeutaDao.update(terapeuta)
        }
    }

    fun deleteTerapeuta(terapeuta: Terapeuta) {
        viewModelScope.launch {
            terapeutaDao.delete(terapeuta)
        }
    }
}
