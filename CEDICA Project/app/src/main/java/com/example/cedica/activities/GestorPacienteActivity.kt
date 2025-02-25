package com.example.cedica.activities

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cedica.R
import com.example.cedica.data.db.PacienteDatabase
import com.example.cedica.data.entities.Paciente
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cedica.repository.PacienteRepository
import com.example.cedica.utilities.DataStoreHelper
import com.example.cedica.viewmodels.PacienteViewModel
import com.example.cedica.viewmodels.PacienteViewModelFactory

class GestorPacienteActivity : ComponentActivity() {
    private lateinit var viewModel: PacienteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Crear el DAO y Repository
        val pacienteDao = PacienteDatabase.getDatabase(this).pacienteDao()
        val repository = PacienteRepository(pacienteDao)
        val dataStoreHelper = DataStoreHelper(application)

        // Crear ViewModel con Factory
        viewModel = ViewModelProvider(
            this,
            PacienteViewModelFactory(application, repository, dataStoreHelper)
        ).get(PacienteViewModel::class.java)

        setContent {
            val pacientes by viewModel.pacientes.collectAsStateWithLifecycle()
            val pacienteSeleccionado by viewModel.pacienteSeleccionado.collectAsStateWithLifecycle()

            GestorPacienteScreen(
                pacientes = pacientes,
                pacienteSeleccionado = pacienteSeleccionado,
                onPacienteSelected = { paciente ->
                    viewModel.seleccionarPaciente(paciente)
                    Toast.makeText(this, "Paciente ${paciente.nombre} seleccionado", Toast.LENGTH_SHORT).show()
                },
                viewModel = viewModel
            )
        }

    }
}


@Composable
fun GestorPacienteScreen(
    pacientes: List<Paciente>,
    pacienteSeleccionado: Paciente?,
    onPacienteSelected: (Paciente) -> Unit,
    viewModel: PacienteViewModel
){
    var mostrarFormulario by remember { mutableStateOf(false) }
    var pacienteAEliminar by remember { mutableStateOf<Paciente?>(null) }

    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var observaciones by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.wood_full_background),
            contentDescription = "Fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Gestor de Pacientes", style = TextStyle(fontSize = 28.sp), color = Color.White, modifier = Modifier.padding(8.dp))

            Button(
                onClick = { mostrarFormulario = true },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Agregar Paciente")
            }

            if (mostrarFormulario) {
                AgregarPacienteForm(
                    nombre, apellido, edad, genero, observaciones,
                    onNombreChange = { nombre = it },
                    onApellidoChange = { apellido = it },
                    onEdadChange = { edad = it },
                    onGeneroChange = { genero = it },
                    onObservacionesChange = { observaciones = it },
                    onAgregar = {
                        if (nombre.isNotBlank() && apellido.isNotBlank() && edad.isNotBlank()) {
                            val paciente = Paciente(
                                nombre = nombre,
                                apellido = apellido,
                                edad = edad.toIntOrNull() ?: 0,
                                observaciones = observaciones,
                                genero = genero,
                                avatar = "",
                                nivelDificultad = "Facil"
                            )

                            viewModel.insertarPaciente(paciente)

                            nombre = ""; apellido = ""; edad = ""; genero = ""; observaciones = ""
                            mostrarFormulario = false
                        }
                    }
                )
            }

            LazyColumn {
                items(pacientes) { paciente ->
                    PacienteItem(paciente, viewModel, onEliminarPaciente = { pacienteAEliminar = it })
                }
            }

            pacienteAEliminar?.let { paciente ->
                AlertDialog(
                    onDismissRequest = { pacienteAEliminar = null },
                    title = { Text("Eliminar Paciente") },
                    text = { Text("¿Estás seguro de que deseas eliminar a ${paciente.nombre}?") },
                    confirmButton = {
                        Button(onClick = {
                            viewModel.eliminarPaciente(paciente)
                            pacienteAEliminar = null
                        }) {
                            Text("Eliminar", color = Color.White)
                        }
                    }
                    ,
                    dismissButton = {
                        Button(onClick = { pacienteAEliminar = null }) {
                            Text("Cancelar", color = Color.White)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun PacienteItem(paciente: Paciente, viewModel: PacienteViewModel, onEliminarPaciente: (Paciente) -> Unit) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF5cdb5e))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nombre: ${paciente.nombre} ${paciente.apellido}", color = Color.White, style = MaterialTheme.typography.titleMedium)
            Text(text = "Edad: ${paciente.edad}", color = Color.White)
            Text(text = "Género: ${paciente.genero}", color = Color.White)
            Text(text = "Observaciones: ${paciente.observaciones}", color = Color.White)

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        viewModel.seleccionarPaciente(paciente)
                        Toast.makeText(context, "Paciente ${paciente.nombre} seleccionado", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Text("Seleccionar")
                }

                Button(
                    onClick = { onEliminarPaciente(paciente) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Eliminar")
                }
            }

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        Toast.makeText(context, "Historial de ${paciente.nombre}", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE1B921))
                ) {
                    Text("Historial")
                }

                Button(
                    onClick = {
                        if (paciente.id > 0) { // Verifica que el ID sea válido
                            val intent = Intent(context, ConfiguracionPacienteActivity::class.java).apply {
                                putExtra("PACIENTE_ID", paciente.id) // 🔥 Cambia el key a "PACIENTE_ID"
                            }
                            context.startActivity(intent)
                        } else {
                            Toast.makeText(context, "Error: Paciente no válido", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796b))
                ) {
                    Text("Configurar")
                }
            }
        }
    }
}


@Composable
fun AgregarPacienteForm(
    nombre: String, apellido: String, edad: String, genero: String, observaciones: String,
    onNombreChange: (String) -> Unit, onApellidoChange: (String) -> Unit, onEdadChange: (String) -> Unit,
    onGeneroChange: (String) -> Unit, onObservacionesChange: (String) -> Unit, onAgregar: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFe0f7fa))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(value = nombre, onValueChange = onNombreChange, label = { Text("Nombre") })
            OutlinedTextField(value = apellido, onValueChange = onApellidoChange, label = { Text("Apellido") })
            OutlinedTextField(value = edad, onValueChange = onEdadChange, label = { Text("Edad") })
            OutlinedTextField(value = genero, onValueChange = onGeneroChange, label = { Text("Género") })
            OutlinedTextField(value = observaciones, onValueChange = onObservacionesChange, label = { Text("Observaciones") })

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onAgregar,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text("Agregar Paciente")
            }
        }
    }
}

