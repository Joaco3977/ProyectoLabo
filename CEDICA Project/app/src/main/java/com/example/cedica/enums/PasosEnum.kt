package com.example.cedica.enums

//Preguntar joaco que necesita del paso alcanzado por que dependiendo la dificultad tiene menos pasos por hacer.
enum class PasosEnum(val numPaso: Int) {
    Cabeza(1),
    Cuello(2),
    Paleta(3),
    Lomo(4),
    Panza(5),
    Anca(6),
    Manos(7),
    Patas(8),
    Verija(9),
    CuerpoGeneral(10),
    Crines(11),
    Cola(12),
    VasosSanguineos(13);

    fun getPaso(): Int {
        return numPaso
    }

    fun obtenerPaso(paso: Int): PasosEnum {
        return when (paso) {
            1 -> Cabeza
            2 -> Cuello
            3 -> Paleta
            4 -> Lomo
            5 -> Panza
            6 -> Anca
            7 -> Manos
            8 -> Patas
            9 -> Verija
            10 -> CuerpoGeneral
            11 -> Crines
            12 -> Cola
            13 -> VasosSanguineos
            else -> Cabeza
        }
    }

}