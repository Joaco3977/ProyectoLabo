package com.example.cedica.enums

enum class TiempoDificultadEnum (val tiempo: Long = 0) {
    FACIL(1800),
    MEDIO(900),
    DIFICIL(390);

    fun obtenerTiempo(): Long {
        return tiempo
    }

    fun obtenerTiempo(dificultad : TiempoDificultadEnum): Long {
        return dificultad.obtenerTiempo()
    }

    fun obtenerTiempoFinal(restante:Long) : Long {
        return tiempo - restante
    }

}