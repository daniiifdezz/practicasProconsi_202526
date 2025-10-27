package org.dferna14.project.domain.usecase

import org.dferna14.project.domain.model.Elemento
import org.dferna14.project.domain.repository.ElementoRepository

class GetElementosUseCase(
    private val repository: ElementoRepository
) {

    suspend operator fun invoke(): Result<List<Elemento>> {
        return repository.getElementos()
    }
}