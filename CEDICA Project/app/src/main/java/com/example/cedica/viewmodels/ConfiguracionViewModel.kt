package com.example.cedica.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cedica.utilities.DataStoreHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ConfiguracionViewModel(application: Application) : AndroidViewModel(application) {
    private val dataStoreHelper = DataStoreHelper(application)

    val efectosSonorosVolumen: Flow<Float> = dataStoreHelper.getFloat("efectosSonorosVolumen", 0.5f)
    val ambienteVolumen: Flow<Float> = dataStoreHelper.getFloat("ambienteVolumen", 0.5f)
    val musicaVolumen: Flow<Float> = dataStoreHelper.getFloat("musicaVolumen", 0.5f)
    val musica: Flow<Boolean> = dataStoreHelper.getBoolean("musica", true)



    fun guardarEfectosSonorosVolumen(valor: Float) {
        viewModelScope.launch { dataStoreHelper.setFloat("efectosSonorosVolumen", valor) }
    }

    fun guardarAmbienteVolumen(valor: Float) {
        viewModelScope.launch { dataStoreHelper.setFloat("ambienteVolumen", valor) }
    }

    fun guardarMusicaVolumen(valor: Float) {
        viewModelScope.launch { dataStoreHelper.setFloat("musicaVolumen", valor) }
    }

    fun guardarMusica(valor: Boolean) {
        viewModelScope.launch { dataStoreHelper.setBoolean("musica", valor) }
    }

}
