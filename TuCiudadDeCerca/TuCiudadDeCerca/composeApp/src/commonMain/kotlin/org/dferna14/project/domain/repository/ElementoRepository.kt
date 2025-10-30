package org.dferna14.project.domain.repository

import org.dferna14.project.domain.model.Elemento
import tuciudaddecerca.composeapp.generated.resources.Res


interface ElementoRepository{
    suspend fun getElementos(): Result<List<Elemento>>
    suspend fun getDetalleElementos(id:String): Result<Elemento>
}