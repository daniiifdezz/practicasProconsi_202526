package org.dferna14.project

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.dferna14.project.di.initKoin
//import androidx.compose.ui.window.LocalAppWindow

fun main() {
    initKoin()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "TuCiudadDeCerca",
        ) {
            var pantallaActual by remember { mutableStateOf<Pantalla>(Pantalla.Bienvenida) }


            App(pantallaActual = pantallaActual, onPantallaChange = { pantallaActual = it })
        }
    }
}