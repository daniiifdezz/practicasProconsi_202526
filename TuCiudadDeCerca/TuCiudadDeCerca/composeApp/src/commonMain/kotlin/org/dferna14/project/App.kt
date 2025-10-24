@file:OptIn(ExperimentalMaterial3Api::class)

package org.dferna14.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import moe.tlaster.precompose.PreComposeApp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import moe.tlaster.precompose.PreComposeApp
import org.dferna14.project.domain.model.Elemento
import org.dferna14.project.presentation.ElementoVM
import org.dferna14.project.presentation.navigation.Nav
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import tuciudaddecerca.composeapp.generated.resources.Res
import tuciudaddecerca.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App(viewModel: ElementoVM = ElementoVM()) {
    PreComposeApp {
        MaterialTheme {
            Nav(viewModel = viewModel)
        }
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




