package org.dferna14.project.presentation.listado

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.dferna14.project.domain.model.Elemento
import org.dferna14.project.presentation.ListadoVM
import org.dferna14.project.presentation.utils.FondoLeon
import org.jetbrains.compose.resources.painterResource
import tuciudaddecerca.composeapp.generated.resources.Res
import tuciudaddecerca.composeapp.generated.resources.corazon_icon
import tuciudaddecerca.composeapp.generated.resources.salida
import tuciudaddecerca.composeapp.generated.resources.usuario_icon
import androidx.compose.ui.graphics.Color


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListadoScreen(
    viewModel: ListadoVM,
    onElementoClick: (String) -> Unit,
    onVerFavoritosClick: () -> Unit,
    onVerContactoClick: () -> Unit,
    onVolverInicioClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    FondoLeon {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Puntos de interés ciudad de León",
                            fontWeight = FontWeight.SemiBold
                        )
                    },

                    actions = {

                        var expanded by remember { mutableStateOf(false) }
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Abrir menú"
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false } //cerrar menu al tocar fuera de el
                        ) {
                            DropdownMenuItem(
                                text = { Text("Favoritos") },
                                onClick = {
                                    expanded = false
                                    onVerFavoritosClick()
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Contacto") },
                                onClick = {
                                    expanded = false
                                    onVerContactoClick()
                                }
                            )

                        }
                        IconButton(
                            onClick = onVolverInicioClick,
                            modifier = Modifier.padding(end = 12.dp)
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.salida),
                                contentDescription = "Salir",
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
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator()
                } else if (uiState.error != null) {
                    Text("Error: ${uiState.error}", color = MaterialTheme.colorScheme.error)
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 300.dp),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(uiState.elemento) { elemento ->
                            ElementoItem(
                                elemento = elemento,
                                onClick = {
                                    onElementoClick(elemento.id)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ElementoItem(elemento: Elemento, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF202020).copy(alpha = 0.8f)
        )
    ) {
        if (elemento.urlImagen != null) {
            KamelImage(
                resource = asyncPainterResource(data = elemento.urlImagen),
                contentDescription = "Imagen de ${elemento.nombre}",
                modifier = Modifier
                    //.fillMaxWidth()
                    .height(180.dp)
                    .clickable(onClick = onClick),
                contentScale = ContentScale.Crop
            )
        }

        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = elemento.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f),
                    color = Color.White
                )

                if (elemento.esFavorito) {
                    Text(
                        text = "(Favorito)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer(Modifier.height(4.dp))

            Text(
                text = elemento.descripcionCorta?.replace("&nbsp", "") ?: "",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
        }
    }
}
