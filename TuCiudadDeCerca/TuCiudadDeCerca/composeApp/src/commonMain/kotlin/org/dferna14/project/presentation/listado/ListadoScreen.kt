package org.dferna14.project.presentation.listado

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.semantics.error
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

import org.dferna14.project.domain.model.Elemento
import org.dferna14.project.presentation.ListadoVM


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListadoScreen(
    viewModel: ListadoVM,
    onElementoClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Puntos de Interés") }) }
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
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.elemento) { elemento ->
                        ElementoItem(
                            elemento = elemento,
                            onClick = { onElementoClick(elemento.id) }
                        )
                    }
                }
            }
        }
    }
}


/**
 * Este Composable representa un único ítem de la lista.
 * Ahora es clicable.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ElementoItem(elemento: Elemento, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        if (elemento.urlImagen != null) {
            KamelImage(
                resource = asyncPainterResource(data = elemento.urlImagen),
                contentDescription = "Imagen de ${elemento.nombre}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clickable(onClick = onClick),
                contentScale = ContentScale.Crop
            )
        }

        Column(Modifier.padding(16.dp)) {
            Text(elemento.nombre, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(elemento.descripcionCorta, style = MaterialTheme.typography.bodySmall)
        }
    }
}
