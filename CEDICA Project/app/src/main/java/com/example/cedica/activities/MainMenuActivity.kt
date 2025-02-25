package com.example.cedica.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cedica.models.SingleTextButton
import com.example.cedica.R
import androidx.lifecycle.ViewModelProvider
import com.example.cedica.data.db.PacienteDatabase
import com.example.cedica.repository.PacienteRepository
import com.example.cedica.utilities.DataStoreHelper
import com.example.cedica.viewmodels.ConfiguracionViewModel
import com.example.cedica.viewmodels.PacienteViewModel
import com.example.cedica.viewmodels.PacienteViewModelFactory
import com.example.cedica.viewmodels.TerapeutaViewModel
import com.unity3d.player.UnityPlayerGameActivity

class MainMenuActivity : ComponentActivity() {
    private lateinit var terapeutaViewModel: TerapeutaViewModel
    private lateinit var pacienteViewModel: PacienteViewModel
    private lateinit var configuracionViewModel: ConfiguracionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = PacienteDatabase.getDatabase(this)
        val repository = PacienteRepository(database.pacienteDao())
        val dataStoreHelper = DataStoreHelper(application)

        terapeutaViewModel = ViewModelProvider(this).get(TerapeutaViewModel::class.java)
        pacienteViewModel = ViewModelProvider(
            this,
            PacienteViewModelFactory(application, repository, dataStoreHelper)
        ).get(PacienteViewModel::class.java)
        configuracionViewModel = ViewModelProvider(this).get(ConfiguracionViewModel::class.java)

        setContent {
            MainMenuScreen(terapeutaViewModel, pacienteViewModel, configuracionViewModel)
        }
    }
}

@Composable
fun MainMenuScreen(terapeutaViewModel: TerapeutaViewModel, pacienteViewModel: PacienteViewModel,configuracionViewModel: ConfiguracionViewModel ) {

    val terapeutaSeleccionado by terapeutaViewModel.terapeutaSeleccionado.collectAsStateWithLifecycle(initialValue = null)
    val pacienteSeleccionado by pacienteViewModel.pacienteSeleccionado.collectAsStateWithLifecycle()

    var nombreTerapeuta = terapeutaSeleccionado?.nombre ?: "Ninguno"
    var nombrePaciente = pacienteSeleccionado?.nombre ?: "Ninguno"

    val efectosSonorosVolumen by configuracionViewModel.efectosSonorosVolumen.collectAsStateWithLifecycle(initialValue = 0.5f)
    val musicaVolumen by configuracionViewModel.musicaVolumen.collectAsStateWithLifecycle(initialValue = 0.5f)
    val musica by configuracionViewModel.musica.collectAsStateWithLifecycle(initialValue = true)
    val ambienteVolumen by configuracionViewModel.ambienteVolumen.collectAsStateWithLifecycle(initialValue = 0.5f)

    val nivelDificultad = if (pacienteSeleccionado?.nivelDificultad == "Facil") {
        1
    } else if (pacienteSeleccionado?.nivelDificultad == "Medio") {
        2
    } else {
        3
    }

    LaunchedEffect(pacienteSeleccionado) {
        nombrePaciente = pacienteSeleccionado?.nombre ?: "Ninguno"
    }

    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.grass_sky_background),
            contentDescription = "Imagen de fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Imagen del título del juego
            Image(
                painter = painterResource(id = R.drawable.game_title),
                contentDescription = "Título del juego",
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔥 Mostrar terapeuta y paciente seleccionados en tiempo real
            Text(
                text = "Terapeuta Seleccionado: $nombreTerapeuta",
                color = Color.Black,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Paciente Seleccionado: $nombrePaciente",
                color = Color.Black,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botones del menú
            SingleTextButton(
                buttonText = "Jugar",
                backgroundTexture = R.drawable.wood_texture,
                onClickAction = {
                    val intent = Intent(context, UnityPlayerGameActivity::class.java)
                    intent.putExtra("SOUND_EFFECTS_VOLUME", efectosSonorosVolumen)
                    intent.putExtra("MUSIC_VOLUME", musicaVolumen)
                    intent.putExtra("MUSIC_ENABLED", musica)
                    intent.putExtra("AMBIENT_SOUND_VOLUME", ambienteVolumen)
                    intent.putExtra("GAME_DIFFICULTY", nivelDificultad)
                    context.startActivity(intent)
                },
                textColor = Color.Black
            )
            SingleTextButton(
                buttonText = "Gestionar paciente",
                backgroundTexture = R.drawable.wood_texture,
                onClickAction = {
                    val intent = Intent(context, GestorPacienteActivity::class.java)
                    context.startActivity(intent)
                },
                textColor = Color.Black
            )
            SingleTextButton(
                buttonText = "Configuración",
                backgroundTexture = R.drawable.wood_texture,
                onClickAction = {
                    val intent = Intent(context, ConfiguracionActivity::class.java)
                    context.startActivity(intent)
                },
                textColor = Color.Black
            )
            SingleTextButton(
                buttonText = "Acerca de",
                backgroundTexture = R.drawable.wood_texture,
                onClickAction = {
                    val intent = Intent(context, AcercaDeActivity::class.java)
                    context.startActivity(intent)
                },
                textColor = Color.Black
            )
        }
    }
}
