package org.dferna14.project.presentation.navigation

import androidx.compose.runtime.Composable
import org.dferna14.project.presentation.ElementoVM
import org.dferna14.project.presentation.listado.ListadoScreen
import org.dferna14.project.presentation.detalle.DetalleScreen


import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator






@Composable
fun Nav(viewModel: ElementoVM) {
    val navigator = rememberNavigator()

    NavHost(
        navigator = navigator,
        initialRoute = "/listado"
    ) {
        scene(
            route = "/listado",
        ) {
            ListadoScreen(
                viewModel = viewModel,
                onElementoClick = { elementoId ->
                    navigator.navigate("/detalle/$elementoId")
                }
            )
        }
        scene(
            route = "/detalle/{id}",
        ) { backStackEntry ->

            val elementoId = backStackEntry.path<String>("id")
            DetalleScreen(elementoId = elementoId)
        }
    }
}