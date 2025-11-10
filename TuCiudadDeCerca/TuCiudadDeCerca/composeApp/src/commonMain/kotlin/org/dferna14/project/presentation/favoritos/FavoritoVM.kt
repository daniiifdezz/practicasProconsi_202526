package org.dferna14.project.presentation.favoritos


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.dferna14.project.domain.model.Elemento
import org.dferna14.project.domain.usecase.GetFavoritosUseCase

data class FavoritosUiState(
    val favoritos: List<Elemento> = emptyList()

)

class FavoritosVM(
    private val getFavoritosUseCase: GetFavoritosUseCase
) : ViewModel() {

    val uiState: StateFlow<FavoritosUiState> = getFavoritosUseCase()
        .map { listaFavoritos ->
            FavoritosUiState(favoritos = listaFavoritos)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FavoritosUiState()
        )
}
