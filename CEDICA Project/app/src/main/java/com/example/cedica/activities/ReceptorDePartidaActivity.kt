package com.example.cedica.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.cedica.data.HistorialDatabase
import com.example.cedica.data.entities.EntradaHistorial
import com.example.cedica.enums.TiempoDificultadEnum
import com.example.cedica.viewmodels.PacienteViewModel
import com.example.cedica.viewmodels.TerapeutaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReceptorDePartidaActivity : ComponentActivity() {
    private val pacienteViewModel : PacienteViewModel by viewModels()
    private val terapeutaViewModel : TerapeutaViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Recibir datos del Intent
        val errores = intent.getIntExtra("FALLOS", 0)
        val pasoAlcanzado = intent.getIntExtra("PASO_ALCANZADO", 0)
        val gano = intent.getBooleanExtra("GANO", true)
        val tiempoRestante = intent.getLongExtra("TIEMPO_RESTANTE", 0)
        val pistasUtilizadas = intent.getIntExtra("PISTAS_UTILIZADAS", 0)


        val idPaciente = obtenerIdPaciente()
        val nameTerapeuta = obtenerNombreTerapeuta()
        val fechaHora = System.currentTimeMillis()
        var numeroDeDificultad = 0
        val dificultad = determinarDificultad()
        when(dificultad){
            "FACIL" -> numeroDeDificultad = 1
            "MEDIO" -> numeroDeDificultad = 2
            "DIFICIL" -> numeroDeDificultad = 3
        }
        val puntajeFinal = calcularPuntaje(pasoAlcanzado, errores, numeroDeDificultad)

        // Crear objeto para insertar en BD
        val entrada = EntradaHistorial(
            idPaciente = idPaciente,
            nameTerapeuta = nameTerapeuta,
            aciertos = pasoAlcanzado,
            errores = errores,
            pistasUtilizadas = pistasUtilizadas,
            pasoAlcanzado = pasoAlcanzado,
            fechaHora = fechaHora,
            //Falta calcular tiempo con el enum
            tiempoTotal = tiempoRestante,
            //Falta pensar si la funcion de puntaje esta ok
            puntajeFinal = puntajeFinal,
            dificultad = dificultad,
            gano = gano
        )

        // Insertar en la base de datos
        val db = HistorialDatabase.getDatabase(this)
        val historialDao = db.historialDao()

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                historialDao.insertarEntrada(entrada)
            }

            // Una vez insertado, redirigir a MainMenu
            val intent = Intent(this@ReceptorDePartidaActivity, MainMenuActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun obtenerIdPaciente(): Long {
        return pacienteViewModel.pacienteSeleccionado.value?.id ?: -1L // 👈 Retorna -1 si es null
    }


    private fun obtenerNombreTerapeuta(): String {
        return terapeutaViewModel.terapeutaSeleccionado.value?.nombre ?: "Desconocido"
    }


    private fun calcularPuntaje(aciertos: Int, errores: Int, numeroDificultad:Int): String {
        return "${(aciertos * numeroDificultad) - (errores * numeroDificultad / 2 )} Pts"
    }

    private fun determinarDificultad(): String {
        pacienteViewModel.pacienteSeleccionado.value!!.nivelDificultad.let {
            return it
        }
    }
}

