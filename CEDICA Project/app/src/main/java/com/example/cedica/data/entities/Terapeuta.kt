package com.example.cedica.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "terapeutas")
data class Terapeuta(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String
)