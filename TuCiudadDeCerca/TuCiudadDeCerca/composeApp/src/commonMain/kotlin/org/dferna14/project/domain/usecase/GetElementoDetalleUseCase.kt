package org.dferna14.project.domain.usecase

import org.dferna14.project.domain.model.Elemento
import org.dferna14.project.domain.repository.ElementoRepository


class GetElementoDetalleUseCase(
    private val repository: ElementoRepository
) {

    suspend operator fun invoke(): Result<Elemento> {
        return repository.getDetalleElementos(id)
    }


}