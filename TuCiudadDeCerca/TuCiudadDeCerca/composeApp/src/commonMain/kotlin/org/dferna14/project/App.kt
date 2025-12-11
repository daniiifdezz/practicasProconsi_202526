@file:OptIn(ExperimentalMaterial3Api::class)

package org.dferna14.project

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.dferna14.project.presentation.ListadoVM
import org.dferna14.project.presentation.contacto.ContactoScreen
import org.dferna14.project.presentation.detalle.DetalleScreen
import org.dferna14.project.presentation.detalle.DetalleVM
import org.dferna14.project.presentation.favoritos.FavoritosScreen
import org.dferna14.project.presentation.favoritos.FavoritosVM
import org.dferna14.project.presentation.listado.ListadoScreen
import org.dferna14.project.presentation.welcome.BienvenidaScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

//revisar compose tema de estados, cambio de contenidos
sealed class Pantalla {
    object Listado : Pantalla()
    data class Detalle(val id: String) : Pantalla()
    object Favoritos : Pantalla()

    object Contacto : Pantalla()

    object Bienvenida : Pantalla()

    object Salir : Pantalla()
}
//menu lateral, ir hacia atras, versionado.

@Composable
@Preview
fun App(
    pantallaActual: Pantalla = Pantalla.Bienvenida,
    onPantallaChange: (Pantalla) -> Unit
) {
    MaterialTheme {
        when (val pantalla = pantallaActual) {
            is Pantalla.Bienvenida -> {
                BienvenidaScreen(
                    onEntrarClick = {
                        onPantallaChange(Pantalla.Listado)
                    }

                )

            }

            is Pantalla.Listado -> {
                val listadoVM = koinViewModel<ListadoVM>()

                ListadoScreen(
                    viewModel = listadoVM,
                    onElementoClick = {
                        onPantallaChange(Pantalla.Detalle(it))
                    },
                    onVerFavoritosClick = {
                        onPantallaChange(Pantalla.Favoritos)
                    },
                    onVerContactoClick = {
                        onPantallaChange(Pantalla.Contacto)
                    },
                    onVolverInicioClick = {
                        onPantallaChange(Pantalla.Salir)
                    }
                )
            }

            is Pantalla.Detalle -> {
                val detalleVM = koinViewModel<DetalleVM>(
                    parameters = { parametersOf(pantalla.id) }
                )
                
                DetalleScreen(
                    viewModel = detalleVM,
                    onVolverAtras = {
                        onPantallaChange(Pantalla.Listado)
                    }
                )
            }

            is Pantalla.Favoritos -> {
                val favoritosVM = koinViewModel<FavoritosVM>()

                FavoritosScreen(
                    viewModel = favoritosVM,
                    onVolverAtras = {
                        onPantallaChange(Pantalla.Listado)
                    },
                    onElementoClick = { elementoId ->
                        onPantallaChange(Pantalla.Detalle(elementoId))
                    }
                )
            }

            is Pantalla.Contacto -> {
                ContactoScreen(
                    onVolverAtras = {
                        onPantallaChange(Pantalla.Listado)
                    }
                )
            }

            is Pantalla.Salir -> {
                onPantallaChange(Pantalla.Bienvenida)
            }

        }
    }
}
