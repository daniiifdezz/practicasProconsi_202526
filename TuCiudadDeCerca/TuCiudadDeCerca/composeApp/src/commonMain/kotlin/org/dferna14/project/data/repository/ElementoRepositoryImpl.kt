package org.dferna14.project.data.repository

import kotlinx.coroutines.delay
import org.dferna14.project.domain.model.Elemento
import org.dferna14.project.domain.repository.ElementoRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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

    //simulacion bbdd, para futura implementacion.
    private val favoritosIds = MutableStateFlow<Set<String>>(emptySet())

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


    override suspend fun getDetalleElemento(id: String): Result<Elemento> {
        return try {
            val detalleDto = apiService.getElementosDetalleDto(id)


            val elementoDetalle = detalleDto.toDomain()

            Result.success(elementoDetalle)

        } catch (e: Exception) {
            Result.failure(e)
        }

    }


    //futura implementacion BBDD
    override suspend fun addFavorito(id: String) {
        favoritosIds.value = favoritosIds.value + id
    }

    override suspend fun removeFavorito(id: String) {
        favoritosIds.value = favoritosIds.value - id

    }

    override fun getFavoritos(): Flow<List<Elemento>> {
        return favoritosIds.flatMapLatest { setDeIds ->
            flow {
                if (setDeIds.isEmpty()) {
                    emit(emptyList())
                } else {
                    coroutineScope {
                        val listaDeFavoritos = setDeIds.map { id ->
                            async { getDetalleElemento(id) }
                        }.awaitAll()

                        val favoritosExitosos = listaDeFavoritos.mapNotNull { it.getOrNull() }

                        emit(favoritosExitosos)
                    }
                }
            }
        }
    }
}




