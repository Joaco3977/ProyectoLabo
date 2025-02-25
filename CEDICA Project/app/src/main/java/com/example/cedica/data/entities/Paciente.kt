package com.example.cedica.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pacientes")
data class Paciente(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val apellido: String,
    val edad: Int,
    var observaciones: String,
    val genero: String,
    val avatar: String,

    // Datos de configuracion
    var nivelDificultad: String = "Facil",
)