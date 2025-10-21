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

/*
Llama a ElementoRepository para traer los datos (simulados)
 */
class ElementoVM(
    private val elementoRepository: ElementoRepository = ElementoRepositoryImpl()
) : ViewModel() {
    private val _uiState = MutableStateFlow(ElementoUI())
    val uiState = _uiState.asStateFlow()

    init {
        loadElemento()
    }

    fun loadElemento() {
        viewModelScope.launch {

            _uiState.update { it.copy(isLoading = true) }


            val elementos = elementoRepository.getElementos()

            _uiState.update { it.copy(isLoading = false, elemento = elementos) }
        }
    }
}

