package com.example.cedica.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.cedica.data.db.PacienteDatabase
import com.example.cedica.repository.PacienteRepository
import com.example.cedica.utilities.DataStoreHelper
import com.example.cedica.viewmodels.PacienteViewModel
import com.example.cedica.viewmodels.PacienteViewModelFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cedica.R

class ConfiguracionPacienteActivity : ComponentActivity() {
    private lateinit var viewModel: PacienteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = PacienteDatabase.getDatabase(this)
        val repository = PacienteRepository(database.pacienteDao())
        val dataStoreHelper = DataStoreHelper(application)

        viewModel = ViewModelProvider(
            this,
            PacienteViewModelFactory(application, repository, dataStoreHelper)
        ).get(PacienteViewModel::class.java)

        val pacienteId = intent.getLongExtra("PACIENTE_ID", -1L)

        viewModel.cargarPaciente(pacienteId)

        setContent {
            ConfiguracionScreen(viewModel, pacienteId)
        }
    }

}

@Composable
fun ConfiguracionScreen(viewModel: PacienteViewModel, pacienteId : Long) {
    val selectedPaciente by viewModel.pacienteCargado.collectAsStateWithLifecycle(initialValue = null)
    var dificultad by remember { mutableStateOf("Facil") }
    var observaciones by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(selectedPaciente) {
        selectedPaciente?.let { paciente ->
            dificultad = paciente.nivelDificultad
            observaciones = paciente.observaciones
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.wood_full_background),
            contentDescription = "Fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Configuración del Juego",
                style = TextStyle(fontSize = 28.sp),
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )

            selectedPaciente?.let { paciente ->
                Text(
                    text = "Configuración para ${paciente.nombre} ${paciente.apellido}",
                    style = TextStyle(fontSize = 20.sp),
                    color = Color.White,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Text(text = "Selecciona el nivel de dificultad", color = Color.White, modifier = Modifier.padding(top = 16.dp))
                SimpleDropdownMenu(
                    options = listOf("Facil", "Medio", "Dificil"),
                    selectedOption = dificultad,
                    onOptionSelected = { dificultad = it }
                )

                Text(text = "Observaciones", color = Color.White, modifier = Modifier.padding(top = 16.dp))
                BasicTextField(
                    value = observaciones,
                    onValueChange = { observaciones = it },
                    textStyle = TextStyle(color = Color.White),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.5f), shape = RoundedCornerShape(8.dp))
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.actualizarConfiguracionPaciente(dificultad, observaciones)
                        Toast.makeText(context, "Configuración guardada", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Text("Guardar Configuración", color = Color.White)
                }
            }
        }
    }
}
@Composable
fun SimpleDropdownMenu(options: List<String>, selectedOption: String, onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        TextField(
            value = selectedOption,
            onValueChange = {},  // No permitimos cambios manuales
            label = { Text("Seleccione una opción") },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    text = { Text(text = option, color = Color.Black) }
                )
            }
        }
    }
}
