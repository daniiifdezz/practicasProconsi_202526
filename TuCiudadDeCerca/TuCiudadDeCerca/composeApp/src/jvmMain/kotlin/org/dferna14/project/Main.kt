package org.dferna14.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.dferna14.project.di.initKoin

fun main() {
    initKoin()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "TuCiudadDeCerca",
        ) {

            App()
        }
    }
}