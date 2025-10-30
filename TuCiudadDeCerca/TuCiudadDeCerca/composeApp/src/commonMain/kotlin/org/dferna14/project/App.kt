@file:OptIn(ExperimentalMaterial3Api::class)

package org.dferna14.project

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import org.dferna14.project.data.remote.ktorClient
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.dferna14.project.data.remote.ApiService
import org.dferna14.project.data.repository.ElementoRepositoryImpl
import org.dferna14.project.domain.usecase.GetElementosUseCase
import org.dferna14.project.presentation.ListadoVM
import org.dferna14.project.presentation.listado.ListadoScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {

        val elementoRepository = remember {

            val apiService = ApiService(ktorClient)

            ElementoRepositoryImpl(apiService)

        }

        val getElementosUseCase = remember{
            GetElementosUseCase(elementoRepository)
        }

        val listadoVM = remember { ListadoVM(getElementosUseCase) }

        ListadoScreen(
            viewModel = listadoVM,
            onElementoClick = { elementoId ->
                println("Elemento pulsado: $elementoId")
            }
        )
    }
}


/**
 * @Composable
 * fun cargarImagen(url: String) {
 *     KamelImage(
 *         resource = asyncPainterResource(url),
 *         contentDescription = "Imagen",
 *         modifier = Modifier.fillMaxWidth().padding(8.dp)
 *     )
 * }
 *
 */




