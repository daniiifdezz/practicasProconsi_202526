package org.dferna14.project

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
//import androidx.compose.ui.window.LocalAppWindow

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "TuCiudadDeCerca",
    ) {
        //val window = LocalAppWindow.current


        //LaunchedEffect(Unit) {
         //   window.titleBarDecorations.setDarkTheme(true)
        //}

        App()
    }
}