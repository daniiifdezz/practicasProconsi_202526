package org.dferna14.project.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.dferna14.project.domain.model.Elemento
import org.dferna14.project.domain.repository.ElementoRepository

class GetFavoritosUseCase(
    private val repository: ElementoRepository
) {
    operator fun invoke(): Flow<List<Elemento>> {
        return repository.getFavoritos()
    }
}