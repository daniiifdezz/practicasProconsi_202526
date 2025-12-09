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
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import io.kamel.core.ExperimentalKamelApi
import org.dferna14.project.presentation.utils.FondoLeon
import org.jetbrains.compose.resources.painterResource
import tuciudaddecerca.composeapp.generated.resources.Res
import tuciudaddecerca.composeapp.generated.resources.addFav
import tuciudaddecerca.composeapp.generated.resources.addFavClickado
import tuciudaddecerca.composeapp.generated.resources.corazon_icon
import tuciudaddecerca.composeapp.generated.resources.menu_icon



@OptIn(ExperimentalKamelApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DetalleScreen(
    viewModel: DetalleVM,
    onVolverAtras: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    FondoLeon {

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {

                        Text(
                            "Detalle del lugar de interes",
                            color = Color.White
                        )

                    },
                    navigationIcon = {


                        IconButton(
                            onClick = onVolverAtras,
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Volver atrás",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Black.copy(alpha = 0.4f),
                        titleContentColor = Color.White,
                        actionIconContentColor = Color.White
                    )
                )
            }
        ) { paddingValues ->
            when (val state = uiState) {
                is DetalleUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }

                is DetalleUiState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .verticalScroll(rememberScrollState())
                    ) {
                        //titulo
                        Text(
                            text = state.elemento.nombre,
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                            modifier = Modifier.padding(16.dp),
                            color = Color.White
                        )

                        //boton fav
                        IconButton(
                            onClick = { viewModel.onFavoritoClicked() },
                            modifier = Modifier.padding(start = 16.dp)
                        ) {
                            Icon(
                                imageVector = if (state.elemento.esFavorito) {
                                    Icons.Filled.Favorite
                                } else {
                                    Icons.Outlined.FavoriteBorder
                                },
                                contentDescription = if (state.elemento.esFavorito) "Quitar de favoritos" else "Añadir a favoritos",
                                modifier = Modifier.size(32.dp),
                                tint = if (state.elemento.esFavorito) Color.Red else Color.Gray
                            )

                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        //galeria
                        Text(
                            text = "Galería de imágenes",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color.White
                        )
                        //lazy es reciclable, echarle ojo al concepto
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(state.elemento.galeriaImagenes ?: emptyList()) { imageUrl ->
                                KamelImage(
                                    resource = { asyncPainterResource(data = imageUrl) },

                                    contentDescription = "Imagen de la galería",
                                    modifier = Modifier
                                        .height(180.dp)
                                        .width(280.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                        //descripcion


                        Text(
                            text = "Descripción",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                            textDecoration = TextDecoration.Underline,
                            color = Color.White
                        )
                        Text(
                            text = limpiarHtml(state.elemento.descripcionLarga),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                        state.elemento.curiosidades.forEach { seccion ->
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = seccion.titulo,
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(horizontal = 16.dp),
                                textDecoration = TextDecoration.Underline,
                                color = Color.White
                            )
                            Text(
                                text = limpiarHtml(seccion.contenido),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                        if (state.elemento.listaVideos.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(16.dp))
                            val videoUrl = state.elemento.listaVideos.first()

                            val uriHandler = androidx.compose.ui.platform.LocalUriHandler.current

                            Button(
                                onClick = { uriHandler.openUri(videoUrl) },
                                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                                // colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                            ) {
                                Text("Ver Vídeo")
                            }
                        }
                        val hayContacto = !state.elemento.direccion.isNullOrBlank() ||
                                !state.elemento.telefono.isNullOrBlank() ||
                                !state.elemento.email.isNullOrBlank()

                        if (hayContacto) {
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                text = "Información de interés",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )

                            Column(modifier = Modifier.padding(16.dp)) {
                                state.elemento.direccion?.let {
                                    if (it.isNotBlank()) {
                                        Text(
                                            text = "Dirección: $it",
                                            color = Color.White,
                                            modifier = Modifier.padding(bottom = 4.dp)
                                        )
                                    }
                                }
                                state.elemento.telefono?.let {
                                    if (it.isNotBlank()) {
                                        Text(
                                            text = "Teléfono: $it",
                                            color = Color.White,
                                            modifier = Modifier.padding(bottom = 4.dp)
                                        )
                                    }
                                }
                                state.elemento.email?.let {
                                    if (it.isNotBlank()) {
                                        Text(
                                            text = "Email: $it",
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                    }
                }

                is DetalleUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Error: ${state.message}",
                            color = Color.White
                        )
                    }
                }
            }
        }


    }

}

@OptIn(ExperimentalMaterial3Api::class)
fun limpiarHtml(texto: String?): String {
    return texto?.replace("<br />", "\n")
        ?.replace("<br>", "\n")
        ?.replace("\n\n", "\n")
        ?.replace("&nbsp", "")
        ?: "No disponible"
}
