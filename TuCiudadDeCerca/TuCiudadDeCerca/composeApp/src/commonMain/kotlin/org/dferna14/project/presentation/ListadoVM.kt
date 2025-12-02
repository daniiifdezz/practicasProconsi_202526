package org.dferna14.project.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.dferna14.project.domain.model.AppError
import org.dferna14.project.domain.usecase.GetElementosUseCase
import org.dferna14.project.domain.usecase.GetFavoritosUseCase


class ListadoVM(
    private val getElementosUseCase: GetElementosUseCase,
    private val getFavoritosUseCase: GetFavoritosUseCase
) : ViewModel() {

    //obtenemos favoritos del useCase
    private val favoritosFlow = getFavoritosUseCase()

    val uiState: StateFlow<ElementoUI> = favoritosFlow.flatMapLatest { listaFavoritos ->


        flow {

            //obtenemos datos
            val resultadoApi = getElementosUseCase()

            resultadoApi.fold(
                onSuccess = { elementosDeApi ->

                    val favoritosIdSet = listaFavoritos.map { it.id }.toSet()
                    val elementosCombinados = elementosDeApi.map { elementoApi ->
                        elementoApi.copy(esFavorito = elementoApi.id in favoritosIdSet)
                    }
                    val numMarcadosComoFavoritos = elementosCombinados.count { it.esFavorito }

                    emit(ElementoUI(isLoading = false, elemento = elementosCombinados))
                },
                onFailure = { error ->
                    val mensaje = when (error) {
                        is AppError -> error.message
                        else -> "Ha ocurrido un error inesperado."
                    }
                    emit(ElementoUI(isLoading = false, error = mensaje))
                }
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ElementoUI(isLoading = true)
    )
}
