package com.example.cedica.utilities

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// 🔥 Extensión para obtener el DataStore en el Context
private val Context.dataStore by preferencesDataStore(name = "app_preferences")

class DataStoreHelper(private val context: Context) {

    // 🔹 **Claves para los valores guardados**
    companion object {
        private val FLOAT_KEY = floatPreferencesKey("float_key")
        private val BOOLEAN_KEY = booleanPreferencesKey("boolean_key")
        private val SELECTED_TERAPEUTA_ID_KEY = longPreferencesKey("selected_terapeuta_id")
        private val SELECTED_PACIENTE_ID_KEY = longPreferencesKey("selected_paciente_id")
    }

    // Obtener el terapeuta seleccionado (Flow para ser observable)
    val terapeutaSeleccionado: Flow<Long?> = context.dataStore.data.map { preferences ->
        preferences[SELECTED_TERAPEUTA_ID_KEY]
    }

    // Guardar terapeuta seleccionado
    suspend fun saveSelectedTerapeuta(id: Long) {
        context.dataStore.edit { preferences ->
            preferences[SELECTED_TERAPEUTA_ID_KEY] = id
        }
    }

    suspend fun clearSelectedTerapeuta() {
        context.dataStore.edit { preferences ->
            preferences.remove(SELECTED_TERAPEUTA_ID_KEY)
        }
    }

    // Obtener el ID del paciente seleccionado
    val pacienteSeleccionado: Flow<Long?> = context.dataStore.data.map { preferences ->
        preferences[SELECTED_PACIENTE_ID_KEY]
    }


    // Guardar paciente seleccionado
    suspend fun saveSelectedPaciente(id: Long) {
        context.dataStore.edit { preferences ->
            preferences[SELECTED_PACIENTE_ID_KEY] = id
        }
    }

    // Limpiar paciente seleccionado
    suspend fun clearSelectedPaciente() {
        context.dataStore.edit { preferences ->
            preferences.remove(SELECTED_PACIENTE_ID_KEY)
        }
    }


    // 🔹 **Métodos generales para otros valores**
    fun getFloat(key: String, defaultValue: Float): Flow<Float> {
        return context.dataStore.data.map { it[floatPreferencesKey(key)] ?: defaultValue }
    }

    suspend fun setFloat(key: String, value: Float) {
        context.dataStore.edit { it[floatPreferencesKey(key)] = value }
    }

    fun getBoolean(key: String, defaultValue: Boolean): Flow<Boolean> {
        return context.dataStore.data.map { it[booleanPreferencesKey(key)] ?: defaultValue }
    }

    suspend fun setBoolean(key: String, value: Boolean) {
        context.dataStore.edit { it[booleanPreferencesKey(key)] = value }
    }

    fun getString(key: String, defaultValue: String): Flow<String?> {
        return context.dataStore.data.map { it[stringPreferencesKey(key)] ?: defaultValue }
    }

    suspend fun setString(key: String, value: String) {
        context.dataStore.edit { it[stringPreferencesKey(key)] = value }
    }
}
