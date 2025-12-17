package org.dferna14.project.presentation.listado

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.launch
import org.dferna14.project.domain.model.Elemento
import org.dferna14.project.presentation.ListadoVM
import org.dferna14.project.presentation.contacto.ContactoScreen
import org.dferna14.project.presentation.detalle.DetalleScreen
import org.dferna14.project.presentation.favoritos.FavoritosScreen
import org.dferna14.project.presentation.utils.FondoLeon
import org.dferna14.project.presentation.welcome.BienvenidaScreen
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import tuciudaddecerca.composeapp.generated.resources.Res
import tuciudaddecerca.composeapp.generated.resources.salida

class ListadoScreen : Screen {
    
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<ListadoVM>()
        val uiState by viewModel.uiState.collectAsState()

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(Modifier.height(32.dp))
                    

                    Text(
                        text = "Favoritos",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                scope.launch { drawerState.close() }
                                navigator.push(FavoritosScreen())
                            }
                            .padding(24.dp),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "Contacto",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                scope.launch { drawerState.close() }
                                navigator.push(ContactoScreen())
                            }
                            .padding(24.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        ) {
            FondoLeon {
                Scaffold(
                    containerColor = Color.Transparent,
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    "Puntos de interés León",
                                    fontWeight = FontWeight.SemiBold,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    scope.launch {
                                        if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "Abrir menú",
                                        tint = Color.White
                                    )
                                }
                            },
                            actions = {
                                IconButton(
                                    onClick = { 
                                        navigator.replaceAll(BienvenidaScreen()) 
                                    },
                                    modifier = Modifier.padding(end = 12.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(Res.drawable.salida),
                                        contentDescription = "Salir",
                                        modifier = Modifier.size(24.dp),
                                        tint = Color.White
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
                            CircularProgressIndicator(color = Color.White)
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
                                            navigator.push(DetalleScreen(elemento.id))
                                        }
                                    )
                                }
                            }
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
