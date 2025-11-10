@file:OptIn(ExperimentalMaterial3Api::class)

package org.dferna14.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.dferna14.project.data.local.database
import org.dferna14.project.data.remote.ApiService
import org.dferna14.project.data.remote.ktorClient
import org.dferna14.project.data.repository.ElementoRepositoryImpl
import org.dferna14.project.domain.usecase.GestionarFavoritoUseCase
import org.dferna14.project.domain.usecase.GetElementoDetalleUseCase
import org.dferna14.project.domain.usecase.GetElementosUseCase
import org.dferna14.project.domain.usecase.GetFavoritosUseCase
import org.dferna14.project.presentation.ListadoVM
import org.dferna14.project.presentation.detalle.DetalleScreen
import org.dferna14.project.presentation.detalle.DetalleVM
import org.dferna14.project.presentation.favoritos.FavoritosScreen
import org.dferna14.project.presentation.favoritos.FavoritosVM
import org.dferna14.project.presentation.listado.ListadoScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

sealed class Pantalla {
    object Listado : Pantalla()
    data class Detalle(val id: String) : Pantalla()
    object Favoritos : Pantalla()
}


@Composable
@Preview
fun App() {
    MaterialTheme {

        val elementoRepository = remember {
            val favoritoDAO = database.favoritoDAO()
            ElementoRepositoryImpl(ApiService(ktorClient), favoritoDAO)
        }

        val getElementosUseCase = remember { GetElementosUseCase(elementoRepository) }
        val getElementoDetalleUseCase = remember { GetElementoDetalleUseCase(elementoRepository) }
        val gestionarFavoritoUseCase = remember { GestionarFavoritoUseCase(elementoRepository) }
        val getFavoritosUseCase = remember { GetFavoritosUseCase(elementoRepository) }


        var pantallaActual: Pantalla by remember { mutableStateOf(Pantalla.Listado) }

        when (val pantalla = pantallaActual) {
            is Pantalla.Listado -> {
                val listadoVM = remember { ListadoVM(
                    getElementosUseCase = getElementosUseCase,
                    getFavoritosUseCase = getFavoritosUseCase
                ) }
                ListadoScreen(
                    viewModel = listadoVM,
                    onElementoClick = { elementoId ->
                        pantallaActual = Pantalla.Detalle(elementoId)
                    },
                    onVerFavoritosClick = {
                        pantallaActual = Pantalla.Favoritos
                    }
                )
            }
            is Pantalla.Detalle->{
                val detalleVM = remember(pantalla.id) {
                    DetalleVM(
                        elementoId = pantalla.id,
                        getElementoDetalleUseCase = getElementoDetalleUseCase,
                        gestionarFavoritoUseCase = gestionarFavoritoUseCase,
                        getFavoritosUseCase = getFavoritosUseCase
                    )
                }
                DetalleScreen(
                    viewModel = detalleVM,
                    onVolverAtras = {
                        pantallaActual = Pantalla.Listado
                    }
                )
            }
            is Pantalla.Favoritos ->{
                val favoritosVM = remember { FavoritosVM(getFavoritosUseCase) }

                FavoritosScreen(
                    viewModel = favoritosVM,
                    onVolverAtras = {
                        pantallaActual = Pantalla.Listado
                    },
                    onElementoClick = { elementoId ->
                        pantallaActual = Pantalla.Detalle(elementoId)
                    }
                )
            }
        }
    }
}
