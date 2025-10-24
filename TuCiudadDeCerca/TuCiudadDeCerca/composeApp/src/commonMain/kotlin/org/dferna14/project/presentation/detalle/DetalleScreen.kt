package org.dferna14.project.presentation.detalle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


//De prueba
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleScreen(elementoId: String?) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Detalle del Elemento") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Mostrando detalles para el elemento con ID:")
            Text(elementoId ?: "ID no encontrado", style = MaterialTheme.typography.headlineMedium)
        }
    }
}