package com.example.cedica.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cedica.repository.PacienteRepository
import com.example.cedica.utilities.DataStoreHelper

class PacienteViewModelFactory(
    private val application: Application,
    private val repository: PacienteRepository,
    private val dataStoreHelper: DataStoreHelper
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PacienteViewModel::class.java)) {
            return PacienteViewModel(repository, dataStoreHelper, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
