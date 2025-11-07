package org.dferna14.project.presentation.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.dferna14.project.domain.model.Elemento
import org.dferna14.project.domain.usecase.GestionarFavoritoUseCase
import org.dferna14.project.domain.usecase.GetElementoDetalleUseCase

sealed interface DetalleUiState {
    data class Success(val elemento: Elemento) : DetalleUiState
    data class Error(val message: String) : DetalleUiState
    object Loading : DetalleUiState
}

class DetalleVM(
    private val elementoId: String,
    private val getElementoDetalleUseCase: GetElementoDetalleUseCase,
    private val gestionarFavoritoUseCase: GestionarFavoritoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetalleUiState>(DetalleUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        cargarDetalleElemento()
    }

    fun onFavoritoClicked() {
        viewModelScope.launch {
            val estadoActual = _uiState.value
            if (estadoActual is DetalleUiState.Success) {
                val elementoActual = estadoActual.elemento
                if (elementoActual.esFavorito) {
                    gestionarFavoritoUseCase.removeFavorito(elementoActual.id)
                } else {
                    gestionarFavoritoUseCase.addFavorito(elementoActual.id)
                }


                _uiState.update {
                    (it as DetalleUiState.Success).copy(
                        elemento = elementoActual.copy(esFavorito = !elementoActual.esFavorito)
                    )
                }
            }
        }
    }
    private fun cargarDetalleElemento() {
        _uiState.update { DetalleUiState.Loading }

        viewModelScope.launch {
            getElementoDetalleUseCase(id = elementoId)
                .onSuccess { elementoDetalle ->
                    _uiState.update { DetalleUiState.Success(elementoDetalle) }
                }
                .onFailure { error ->
                    _uiState.update { DetalleUiState.Error(error.message ?: "Error desconocido") }
                }
        }
    }
}
