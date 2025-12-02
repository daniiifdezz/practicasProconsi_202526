package org.dferna14.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            AppAndroid()
        }
    }
}

@Composable
fun AppAndroid() {
    var pantallaActual by remember { mutableStateOf<Pantalla>(Pantalla.Bienvenida) }

    BackHandler(enabled = pantallaActual != Pantalla.Bienvenida) {
        pantallaActual = when (pantallaActual) {
            is Pantalla.Detalle -> Pantalla.Listado
            is Pantalla.Favoritos -> Pantalla.Listado
            is Pantalla.Contacto -> Pantalla.Listado
            else -> Pantalla.Bienvenida
        }
    }

    App(
        pantallaActual = pantallaActual,
        onPantallaChange = { pantallaActual = it }
    )
}

@Preview
@Composable
fun AppAndroidPreview() {
    AppAndroid()
}