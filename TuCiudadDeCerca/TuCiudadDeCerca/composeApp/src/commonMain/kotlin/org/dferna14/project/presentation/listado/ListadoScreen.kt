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
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.dferna14.project.domain.model.Elemento
import org.dferna14.project.presentation.ListadoVM
import org.jetbrains.compose.resources.painterResource
import tuciudaddecerca.composeapp.generated.resources.Res
import tuciudaddecerca.composeapp.generated.resources.corazon_icon
import tuciudaddecerca.composeapp.generated.resources.salida
import tuciudaddecerca.composeapp.generated.resources.usuario_icon


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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Puntos de interés ciudad de León",
                        fontWeight = FontWeight.SemiBold)},
                actions = {

                    IconButton(
                        onClick = onVerFavoritosClick,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.corazon_icon),
                            contentDescription = "Ver favoritos",
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    IconButton(
                        onClick = onVerContactoClick,
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.usuario_icon),
                            contentDescription = "Contacto",
                            modifier = Modifier.size(24.dp)
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
                }


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



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ElementoItem(elemento: Elemento, onClick: () -> Unit) {
    Card{
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
                    modifier = Modifier.weight(1f)
                )

                if (elemento.esFavorito) {
                    Text(
                        text = "(Favorito)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer (Modifier.height(4.dp))

            Text(text = elemento.descripcionCorta.replace("&nbsp", ""), style = MaterialTheme.typography.bodySmall)
        }
    }
}
