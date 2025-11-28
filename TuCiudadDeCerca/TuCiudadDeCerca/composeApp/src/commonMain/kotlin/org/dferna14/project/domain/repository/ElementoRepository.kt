package org.dferna14.project.domain.repository

import kotlinx.coroutines.flow.Flow
import org.dferna14.project.domain.model.Elemento
import tuciudaddecerca.composeapp.generated.resources.Res

//escalabilidad
interface ElementoRepository{
    suspend fun getElementos(): Result<List<Elemento>>
    suspend fun getDetalleElemento(id:String): Result<Elemento>

    suspend fun addFavorito(id: String)

    suspend fun removeFavorito(id: String)

     fun getFavoritos(): Flow<List<Elemento>>

}