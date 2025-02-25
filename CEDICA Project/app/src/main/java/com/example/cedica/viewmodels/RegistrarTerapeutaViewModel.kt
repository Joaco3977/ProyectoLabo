package com.example.cedica.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cedica.data.db.TerapeutaDatabase
import com.example.cedica.data.entities.Terapeuta
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistrarTerapeutaViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = TerapeutaDatabase.getDatabase(application).terapeutaDao()

    private val _nombre = MutableStateFlow("")
    val nombre = _nombre.asStateFlow()

    fun actualizarNombre(nuevoNombre: String) {
        _nombre.value = nuevoNombre
    }

    fun registrarTerapeuta(onTerapeutaRegistrado: () -> Unit) {
        if (_nombre.value.isNotEmpty()) {
            viewModelScope.launch {
                dao.insert(Terapeuta(nombre = _nombre.value))
                onTerapeutaRegistrado()
            }
        }
    }
}
