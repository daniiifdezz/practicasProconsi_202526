package org.dferna14.project.presentation

import androidx.compose.animation.core.copy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.dferna14.project.domain.repository.ElementoRepository
import org.dferna14.project.data.repository.ElementoRepositoryImpl
import org.dferna14.project.domain.usecase.GetElementosUseCase
import org.dferna14.project.domain.usecase.GetFavoritosUseCase


//clicl img de ui -> navegador -> detalleScreen(id) -> detalleVM -> useCase -> repo
class ListadoVM(

    private val getElementosUseCase: GetElementosUseCase,
    private val getFavoritosUseCase: GetFavoritosUseCase

) : ViewModel() {
    private val _uiState = MutableStateFlow(ElementoUI())
    val uiState = _uiState.asStateFlow()


    init {
        loadElemento()
    }

    fun loadElemento() {
        viewModelScope.launch {

            _uiState.update { it.copy(isLoading = true, error = null) }

            val elementosResult = getElementosUseCase()
            val favoritosFlow = getFavoritosUseCase()

            elementosResult.onSuccess { elementosFromApi ->

                //escuchamos los dos flujos y se cambian los datos tras actualizaciones
                combine(favoritosFlow) { favoritos ->

                    //ids marcados
                    val favoritosIdSet = favoritos.first().map { it.id }.toSet()


                    val elementosCombinados = elementosFromApi.map { elementoApi ->
                        elementoApi.copy(esFavorito = elementoApi.id in favoritosIdSet)
                    }
                    elementosCombinados
                }.collect { elementosFinales ->
                    _uiState.update {
                        it.copy(isLoading = false, elemento = elementosFinales)
                    }
                }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, error = error.message) }
            }
        }
    }
}

