package com.example.cedica.models

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SingleTextButton (buttonText: String, textColor: Color = Color.White, backgroundTexture: Int, onClickAction: () -> Unit) {
    Button(
        onClick = onClickAction,
        modifier = Modifier
            .wrapContentWidth()
            .height(70.dp)
            .padding(16.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(modifier = Modifier.wrapContentWidth()) {
            // Imagen de fondo ajustada al tamaño del botón
            Image(
                painter = painterResource(id = backgroundTexture),
                contentDescription = "Fondo de botón",
                modifier = Modifier.wrapContentWidth(),
                contentScale = ContentScale.Crop
            )
            // Texto del botón encima de la imagen y centrado
            Text(
                text = buttonText,
                modifier = Modifier.align(Alignment.Center),
                color = textColor,
                maxLines = 1,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
            )
        }
    }
}