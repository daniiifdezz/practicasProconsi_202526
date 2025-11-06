@file:OptIn(ExperimentalMaterial3Api::class)

package org.dferna14.project

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.dferna14.project.data.remote.ApiService
import org.dferna14.project.data.remote.ktorClient
import org.dferna14.project.data.repository.ElementoRepositoryImpl
import org.dferna14.project.domain.usecase.GetElementoDetalleUseCase
import org.dferna14.project.domain.usecase.GetElementosUseCase
import org.dferna14.project.presentation.ListadoVM
import org.dferna14.project.presentation.detalle.DetalleScreen
import org.dferna14.project.presentation.detalle.DetalleVM
import org.dferna14.project.presentation.listado.ListadoScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {

        val elementoRepository = remember { ElementoRepositoryImpl(ApiService(ktorClient)) }
        val getElementosUseCase = remember { GetElementosUseCase(elementoRepository) }
        val getElementoDetalleUseCase = remember { GetElementoDetalleUseCase(elementoRepository) }


        var pantallaActual: String? by remember { mutableStateOf(null) }

        if (pantallaActual == null) {
            val listadoVM = remember { ListadoVM(getElementosUseCase) }
            ListadoScreen(
                viewModel = listadoVM,
                onElementoClick = { elementoId ->
                    println("App.kt recibe ID para navegar: $elementoId")
                    pantallaActual = elementoId
                }
            )
        } else {

            val detalleVM = remember(pantallaActual) {
                println("Creando DetalleVM para ID: $pantallaActual")
                DetalleVM(
                    elementoId = pantallaActual!!,
                    getElementoDetalleUseCase = getElementoDetalleUseCase
                )
            }
            DetalleScreen(
                viewModel = detalleVM,
                onVolverAtras = {
                    pantallaActual = null
                }
            )
        }
    }
}
