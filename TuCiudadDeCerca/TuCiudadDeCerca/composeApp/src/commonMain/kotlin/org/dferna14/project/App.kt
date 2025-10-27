@file:OptIn(ExperimentalMaterial3Api::class)

package org.dferna14.project

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.dferna14.project.presentation.ElementoVM
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(viewModel: ElementoVM = ElementoVM()) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text("fjevajbv")
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




