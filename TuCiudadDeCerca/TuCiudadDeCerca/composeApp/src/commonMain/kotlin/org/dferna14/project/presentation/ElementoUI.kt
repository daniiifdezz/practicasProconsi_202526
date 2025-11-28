package org.dferna14.project.presentation

import org.dferna14.project.domain.model.Elemento

/*
Pedimos los datos al repo.
Representamos lo que la pantalla puede necesitar.
 */
//gestionar errores
data class ElementoUI(
    val elemento: List<Elemento> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)
