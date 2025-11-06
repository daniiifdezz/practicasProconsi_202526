package org.dferna14.project.presentation

import androidx.compose.animation.core.copy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.dferna14.project.domain.repository.ElementoRepository
import org.dferna14.project.data.repository.ElementoRepositoryImpl
import org.dferna14.project.domain.usecase.GetElementosUseCase


//clicl img de ui -> navegador -> detalleScreen(id) -> detalleVM -> useCase -> repo
class ListadoVM(
    private val getElementosUseCase: GetElementosUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ElementoUI())
    val uiState = _uiState.asStateFlow()


    init {
        loadElemento()
    }

    fun loadElemento() {
        viewModelScope.launch {

            _uiState.update { it.copy(isLoading = true, error = null) }


            getElementosUseCase().onSuccess { elementos ->
                _uiState.update { it.copy(isLoading = false, elemento = elementos) }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, error = error.message) }
            }
        }
    }
}

