package org.dferna14.project.data.repository

import kotlinx.coroutines.delay
import org.dferna14.project.domain.model.Elemento
import org.dferna14.project.domain.repository.ElementoRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.dferna14.project.data.remote.ApiService
import org.dferna14.project.data.remote.dto.ApiResult
import org.dferna14.project.data.remote.dto.ElementoDTO
import org.dferna14.project.data.remote.dto.ElementoDetalleDTO
import org.dferna14.project.data.remote.ktorClient
import org.dferna14.project.data.remote.dto.MediaDTO
import org.dferna14.project.data.mapper.toDomain


/*
Devolvemos elementos simulados, aqui entiendo que ira KTOR
El VM recibe la llista y actualiza su uiState
 */
class ElementoRepositoryImpl(private val apiService: ApiService) : ElementoRepository {


    override suspend fun getElementos(): Result<List<Elemento>> {
        delay(2000)
        return try {
            //aqui esperamos al objeto
            val response = apiService.getElementosDto()

            //sacamos la lista del objeto
            val dtos = response.fichas

            //convertimos la lista de DTO a lista de elementos de nuestro dominio
            val elementos = dtos.map { it.toDomain() }

            Result.success(elementos)

        } catch (e: Exception) {
            println("Error al obtener los elementos del JSON ${e.message}")
            Result.failure(e)
        }
    }



    override suspend fun getDetalleElemento(id:String): Result<Elemento> {
        delay(2000)
        return try {
            println("Repositorio llama a ApiService con ID: $id")
            val detalleDto = apiService.getElementosDetalleDto(id)


            val elementoDetalle = detalleDto.toDomain()

            Result.success(elementoDetalle)

        } catch (e: Exception) {
            println("Error al obtener el detalle del elemento con id $id: ${e.message}")
            Result.failure(e)
        }

    }
}