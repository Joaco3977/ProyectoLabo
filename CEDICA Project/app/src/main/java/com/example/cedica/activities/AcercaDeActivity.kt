package com.example.cedica.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.cedica.R

class AcercaDeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AcercaDeScreen()
        }
    }
}

@Composable
fun AcercaDeScreen() {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo de pantalla
        BackgroundImage()

        // Botón de volver
        BackButton {
            val intent = Intent(context, MainMenuActivity::class.java)
            context.startActivity(intent)
        }

        // Contenido principal
        Content()
    }
}

@Composable
fun BackgroundImage() {
    Image(
        painter = painterResource(id = R.drawable.wood_full_background),
        contentDescription = "Imagen de fondo",
        modifier = Modifier.fillMaxSize(),
        contentScale = androidx.compose.ui.layout.ContentScale.Crop
    )
}

@Composable
fun BackButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .padding(16.dp)

    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Volver",
            tint = Color.Black
        )
    }
}

@Composable
fun Content() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título principal
        Text(
            text = "Acerca de Nosotros",
            style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.Black),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Descripción
        Text(
            text = "Esta aplicación fue desarrollada por alumnos de la Facultad de Informática " +
                    "de la Universidad Nacional de La Plata en la asignatura Laboratorio de Software, " +
                    "en conjunto con CEDICA.",
            style = TextStyle(fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Información de contacto
        Text(
            text = "Información de Contacto",
            style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        ContactInfo(
            name = "Joaquín Diez",
            email = "joaquindiez3977@gmail.com"
        )
        ContactInfo(
            name = "Renzo Gigena",
            email = "renzogigena1@gmail.com"
        )
    }
}

@Composable
fun ContactInfo(name: String, email: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = name,
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        )
        Text(
            text = email,
            style = TextStyle(fontSize = 16.sp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAcercaDeScreen() {
    AcercaDeScreen()
}
