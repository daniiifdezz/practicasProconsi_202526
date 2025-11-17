package org.dferna14.project.presentation.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource
import tuciudaddecerca.composeapp.generated.resources.Res
import tuciudaddecerca.composeapp.generated.resources.leonciudad

@Composable
fun FondoLeon(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(Res.drawable.leonciudad),
            contentDescription = "Fondo de la ciudad de León",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.7f))
        )


        content()
    }
}