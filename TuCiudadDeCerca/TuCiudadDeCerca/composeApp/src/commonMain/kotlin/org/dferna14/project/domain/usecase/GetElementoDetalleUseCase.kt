package org.dferna14.project.domain.usecase

import org.dferna14.project.domain.model.Elemento
import org.dferna14.project.domain.repository.ElementoRepository

//lo declaramos en la VM indicada
class GetElementoDetalleUseCase(
    private val repository: ElementoRepository
) {

    suspend operator fun invoke(id: String): Result<Elemento> {
        return repository.getDetalleElemento(id)
    }


}