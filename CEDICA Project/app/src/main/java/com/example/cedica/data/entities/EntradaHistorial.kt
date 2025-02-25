package com.example.cedica.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EntradaHistorial(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val idPaciente: Long,
    val nameTerapeuta: String,
    val aciertos: Int,
    val errores: Int,
    val pistasUtilizadas: Int,
    val pasoAlcanzado: Int,
    val fechaHora: Long,
    val tiempoTotal: Long,
    val puntajeFinal: String,
    val dificultad: String,
    val gano: Boolean,

)
