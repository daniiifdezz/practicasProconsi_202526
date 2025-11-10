package org.dferna14.project.presentation.detalle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleScreen(
    viewModel: DetalleVM,
    onVolverAtras: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {

                    Text("Detalle del Elemento")
                },
                actions = {
                    Button(
                        onClick = onVolverAtras,
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Text("Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        when (val state = uiState) {
            is DetalleUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is DetalleUiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                ) {

                    Text(
                        text = state.elemento.nombre,
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                        modifier = Modifier.padding(16.dp)
                    )

                    Button(
                        onClick = { viewModel.onFavoritoClicked() },
                        modifier = Modifier.align(Alignment.Start)
                    ) {
                        Text(if (state.elemento.esFavorito) "Quitar de favoritos" else "Agregar a favoritos")
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Galería de imágenes",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.elemento.galeriaImagenes ?: emptyList()) { imageUrl ->
                            KamelImage(
                                resource = asyncPainterResource(data = imageUrl),
                                contentDescription = "Imagen de la galería",
                                modifier = Modifier
                                    .height(180.dp)
                                    .width(280.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Text(
                        text = "Descripción",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                        textDecoration = TextDecoration.Underline
                    )
                    Text(
                        text = state.elemento.descripcionLarga ?: "No disponible",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                }
            }
            is DetalleUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error: ${state.message}",
                        color = Color.Red
                    )
                }
            }
        }
    }
}
