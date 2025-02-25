package com.example.cedica.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cedica.R
import com.example.cedica.viewmodels.ConfiguracionViewModel

class ConfiguracionActivity : ComponentActivity() {
    private val viewModel: ConfiguracionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConfiguracionScreen(viewModel, onBackPressed = { finish() })
        }
    }
}

@Composable
fun ConfiguracionScreen(viewModel: ConfiguracionViewModel, onBackPressed: () -> Unit) {
    val efectosSonorosVolumen by viewModel.efectosSonorosVolumen.collectAsStateWithLifecycle(initialValue = 0.5f)
    val musicaVolumen by viewModel.musicaVolumen.collectAsStateWithLifecycle(initialValue = 0.5f)
    val musica by viewModel.musica.collectAsStateWithLifecycle(initialValue = true)
    val ambienteVolumen by viewModel.ambienteVolumen.collectAsStateWithLifecycle(initialValue = 0.5f)


    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.wood_full_background),
            contentDescription = "Imagen de fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Botón de volver al menú principal
        IconButton(
            onClick = { onBackPressed() },
            modifier = Modifier.padding(16.dp).align(Alignment.TopStart)
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                "Configuración",
                style = TextStyle(fontSize = 36.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            ConfiguracionSlider("Volumen de Efectos Sonoros", efectosSonorosVolumen) {
                viewModel.guardarEfectosSonorosVolumen(it)
            }

            ConfiguracionSwitch("Musica",musica) {
                viewModel.guardarMusica(it)
            }

            ConfiguracionSlider("Volumen de Musica", musicaVolumen) {
                viewModel.guardarMusicaVolumen(it)
            }

            ConfiguracionSlider("Volumen de ambiente", ambienteVolumen) {
                viewModel.guardarAmbienteVolumen(it)
            }
        }
    }
}

@Composable
fun ConfiguracionSlider(texto: String, valor: Float, onValorCambio: (Float) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF5E8E3E)) // Verde más oscuro
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(texto, style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End, // Alinea a la derecha
                verticalAlignment = Alignment.CenterVertically
            ) {
                Slider(
                    value = valor,
                    onValueChange = onValorCambio,
                    valueRange = 0f..1f,
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFF1B5E20), // Verde oscuro
                        activeTrackColor = Color(0xFF1B5E20) // Verde oscuro
                    ),
                    modifier = Modifier.width(500.dp) // Ajusta el ancho del slider
                )
            }
        }
    }
}

@Composable
fun ConfiguracionSwitch(texto: String, valor: Boolean, onValorCambio: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween, // Separa el texto y el switch
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(texto, style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold))
        Switch(
            checked = valor,
            onCheckedChange = onValorCambio,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF1B5E20) // Verde oscuro
            )
        )
    }
}
