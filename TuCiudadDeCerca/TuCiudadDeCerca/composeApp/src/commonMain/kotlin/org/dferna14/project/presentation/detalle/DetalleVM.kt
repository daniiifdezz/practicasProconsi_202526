package org.dferna14.project.presentation.detalle

import androidx.compose.animation.core.copy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.dferna14.project.domain.model.Elemento
import org.dferna14.project.domain.usecase.GestionarFavoritoUseCase
import org.dferna14.project.domain.usecase.GetElementoDetalleUseCase
import org.dferna14.project.domain.usecase.GetFavoritosUseCase

sealed interface DetalleUiState {
    data class Success(val elemento: Elemento) : DetalleUiState
    data class Error(val message: String) : DetalleUiState
    object Loading : DetalleUiState
}

class DetalleVM(
    private val elementoId: String,
    private val getElementoDetalleUseCase: GetElementoDetalleUseCase,
    private val gestionarFavoritoUseCase: GestionarFavoritoUseCase,
    private val getFavoritosUseCase: GetFavoritosUseCase
) : ViewModel() {

    //private val _uiState = MutableStateFlow<DetalleUiState>(DetalleUiState.Loading)
    //val uiState = _uiState.asStateFlow()

    private val detalleElementoFlow = flow {
        emit(getElementoDetalleUseCase(id = elementoId))
    }

    //reacciondo a cambios
    private val favoritosFlow = getFavoritosUseCase()

    //estodo final
    val uiState: StateFlow<DetalleUiState> = combine(
        detalleElementoFlow,
        favoritosFlow
    ) { resultadoDetalle, listaFavoritos ->

        resultadoDetalle.fold(
            onSuccess = { elementoDesdeApi ->
                val favoritosIdSet = listaFavoritos.map { it.id }.toSet()
                val esFavoritoInicial = elementoId in favoritosIdSet

                DetalleUiState.Success(
                    elemento = elementoDesdeApi.copy(esFavorito = esFavoritoInicial)
                )
            },
            onFailure = { error ->
                DetalleUiState.Error(error.message ?: "Error desconocido")
            }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DetalleUiState.Loading
    )


    fun onFavoritoClicked() {
        viewModelScope.launch {
            val estadoActual = uiState.value

            if (estadoActual is DetalleUiState.Success) {
                val elementoActual = estadoActual.elemento

                if (elementoActual.esFavorito) {
                    gestionarFavoritoUseCase.removeFavorito(elementoActual.id)
                } else {
                    gestionarFavoritoUseCase.addFavorito(elementoActual.id)
                }

            }
        }
    }


}
