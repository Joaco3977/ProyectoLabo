package com.example.cedica.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cedica.viewmodels.TerapeutaViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cedica.R
import com.example.cedica.data.entities.Terapeuta

class ElegirTerapeutaActivity : ComponentActivity() {
    private val viewModel: TerapeutaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val terapeutas by viewModel.terapeutas.collectAsStateWithLifecycle()
            val terapeutaSeleccionado by viewModel.terapeutaSeleccionado.collectAsStateWithLifecycle()

            ElegirTerapeutaScreen(
                terapeutas = terapeutas,
                terapeutaSeleccionado = terapeutaSeleccionado,
                onTerapeutaSelected = { terapeuta ->
                    viewModel.selectTerapeuta(terapeuta)
                    startActivity(Intent(this, MainMenuActivity::class.java))
                    finish()
                },
                onDelete = { terapeuta ->
                    viewModel.deleteTerapeuta(terapeuta)
                },
                onRegistrarTerapeuta = {
                    startActivity(Intent(this, RegistrarTerapeutaActivity::class.java))
                }
            )
        }
    }
}



@Composable
fun ElegirTerapeutaScreen(
    terapeutas: List<Terapeuta>,
    terapeutaSeleccionado: Terapeuta?,
    onTerapeutaSelected: (Terapeuta) -> Unit,
    onRegistrarTerapeuta: () -> Unit,
    onDelete: (Terapeuta) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.wood_full_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Text(
                text = "Selecciona un terapeuta!",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.Black),
                modifier = Modifier.padding(16.dp)
            )


            if (terapeutas.isEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "No hay terapeutas disponibles.",
                        style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black)
                    )
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(terapeutas) { terapeuta ->
                        TerapeutaItem(terapeuta = terapeuta, onClick = { onTerapeutaSelected(terapeuta) })
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onRegistrarTerapeuta() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                Text("Registrar Nuevo Terapeuta", style = TextStyle(fontWeight = FontWeight.Bold, color = Color.White))
            }
        }
    }
}


@Composable
fun TerapeutaItem(
    terapeuta: Terapeuta,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2E7D32))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = terapeuta.nombre, style = MaterialTheme.typography.titleMedium)
        }
        Column (modifier = Modifier.padding(16.dp)) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                Text("Eliminar perfil")
            }
        }
    }
}
