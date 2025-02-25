package com.example.cedica.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cedica.data.entities.Paciente
import com.example.cedica.repository.PacienteRepository
import com.example.cedica.utilities.DataStoreHelper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PacienteViewModel(
    private val repository: PacienteRepository,
    private val dataStore: DataStoreHelper,
    application: Application
) : AndroidViewModel(application) {

    private val _pacientes = MutableStateFlow<List<Paciente>>(emptyList())
    val pacientes: StateFlow<List<Paciente>> = _pacientes.asStateFlow()

    private val _pacienteSeleccionado = MutableStateFlow<Paciente?>(null)
    val pacienteSeleccionado: StateFlow<Paciente?> = _pacienteSeleccionado.asStateFlow()

    private val _pacienteCargado = MutableStateFlow<Paciente?>(null)
    val pacienteCargado: StateFlow<Paciente?> = _pacienteCargado.asStateFlow()

    init {
        cargarPacientes()
        cargarPacienteSeleccionado()
    }

    private fun cargarPacientes() {
        viewModelScope.launch {
            repository.getAllPacientes().collect { lista ->
                _pacientes.value = lista
            }
        }
    }

    private fun cargarPacienteSeleccionado() {
        viewModelScope.launch {
            dataStore.pacienteSeleccionado.collect { id ->
                if (id != null && id > 0) {
                    val paciente = repository.getPacienteById(id)
                    _pacienteSeleccionado.value = paciente
                } else {
                    _pacienteSeleccionado.value = null  // 🔥 Asegurar que la UI detecte el cambio
                }
            }
        }
    }

    fun seleccionarPaciente(paciente: Paciente) {
        viewModelScope.launch {
            dataStore.saveSelectedPaciente(paciente.id)
            _pacienteSeleccionado.value = paciente
        }
    }


    fun actualizarConfiguracionPaciente(nuevaDificultad: String, nuevasObservaciones: String) {
        viewModelScope.launch {
            val pacienteActual = _pacienteCargado.value ?: return@launch
            val pacienteActualizado = pacienteActual.copy(nivelDificultad = nuevaDificultad, observaciones = nuevasObservaciones)
            repository.update(pacienteActualizado)
            _pacienteCargado.value = pacienteActualizado
        }
    }

    fun insertarPaciente(paciente: Paciente) {
        viewModelScope.launch {
            val idGenerado = repository.insert(paciente)
            if (idGenerado > 0) {
                _pacientes.value = _pacientes.value + paciente.copy(id = idGenerado)
            }
        }
    }

    fun eliminarPaciente(paciente: Paciente) {
        viewModelScope.launch {
            repository.delete(paciente)

            // 🔥 Si el paciente eliminado es el seleccionado, limpiarlo en DataStore
            if (_pacienteSeleccionado.value?.id == paciente.id) {
                dataStore.clearSelectedPaciente()
                _pacienteSeleccionado.value = null // 🔥 Actualizar el estado en el ViewModel
            }

            // 🔥 Actualizar la lista de pacientes
            _pacientes.value = _pacientes.value.filter { it.id != paciente.id }
        }
    }


    fun cargarPaciente(pacienteId: Long) {
        viewModelScope.launch {
            val paciente = repository.getPacienteById(pacienteId)
            _pacienteCargado.value = paciente
        }
    }

    fun obtenerPacienteCargado(): Paciente? {
        return _pacienteCargado.value
    }
}
