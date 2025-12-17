package org.dferna14.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.dferna14.project.presentation.welcome.BienvenidaScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {

        Navigator(BienvenidaScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }
}
