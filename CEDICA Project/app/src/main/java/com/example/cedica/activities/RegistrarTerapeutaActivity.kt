package com.example.cedica.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cedica.viewmodels.RegistrarTerapeutaViewModel

class RegistrarTerapeutaActivity : ComponentActivity() {
    private val viewModel: RegistrarTerapeutaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistrarTerapeutaScreen(viewModel, onTerapeutaRegistrado = { finish() })
        }
    }
}

@Composable
fun RegistrarTerapeutaScreen(
    viewModel: RegistrarTerapeutaViewModel,
    onTerapeutaRegistrado: () -> Unit
) {
    val nombre by viewModel.nombre.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Registrar Nuevo Terapeuta",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = { viewModel.actualizarNombre(it) },
                label = { Text("Nombre del Terapeuta") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.registrarTerapeuta(onTerapeutaRegistrado) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
            ) {
                Text("Registrar", color = Color.White)
            }
        }
    }
}
