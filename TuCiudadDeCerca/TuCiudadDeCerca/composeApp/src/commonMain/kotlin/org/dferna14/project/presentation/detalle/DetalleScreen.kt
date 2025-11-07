package org.dferna14.project.presentation.detalle

import androidx.compose.foundation.layout.Arrangement
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



@Composable
fun DetalleScreen(
    viewModel: DetalleVM,
    onVolverAtras: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val state = uiState) {
            is DetalleUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(top = 64.dp))
            }
            is DetalleUiState.Success -> {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = state.elemento.nombre,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(16.dp)
                    )

                    Button(
                        onClick = {
                            println("Botón presionado")
                        },
                        modifier = Modifier.padding(horizontal = 16.dp)

                    ){

                        Text(if (state.elemento.esFavorito) "Quitar de favoritos" else "Agregar a favoritos")
                    }

                    Text(
                        text = "Galería",
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
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    )
                    Text(
                        text = state.elemento.descripcionLarga ?: "No disponible",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }


                Spacer(Modifier.weight(1f))
                Button(
                    onClick = onVolverAtras,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Volver a la lista")
                }
            }
            is DetalleUiState.Error -> {
                Text(
                    text = "Error: ${state.message}",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 64.dp)
                )
            }
        }
    }
}
