package org.dferna14.project.data.repository

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.io.IOException
import org.dferna14.project.data.local.FavoritoDAO
import org.dferna14.project.data.local.FavoritoEntidad
import org.dferna14.project.data.mapper.toDomain
import org.dferna14.project.data.remote.ApiService
import org.dferna14.project.domain.model.AppError
import org.dferna14.project.domain.model.Elemento
import org.dferna14.project.domain.repository.ElementoRepository


class ElementoRepositoryImpl(
    private val apiService: ApiService,
    private val favoritoDAO: FavoritoDAO
) : ElementoRepository {

    //simulacion bbdd, para futura implementacion.
    //private val favoritosIds = MutableStateFlow<Set<String>>(emptySet())

    override suspend fun getElementos(): Result<List<Elemento>> {
        return try {
            //aqui esperamos al objeto
            val response = apiService.getElementosDto()

            //sacamos la lista del objeto
            val dtos = response.fichas

            //convertimos la lista de DTO a lista de elementos de nuestro dominio
            val elementos = dtos.map { it.toDomain() }

            Result.success(elementos)

        } catch (e: RedirectResponseException) {
            println("Error del cliente (redirección): ${e.message}")
            Result.failure(AppError.ServerError("Error de redirección."))
        } catch (e: ClientRequestException) {
            println("Error del cliente (4xx): ${e.message}")
            Result.failure(AppError.ServerError("Error de cliente."))
        } catch (e: ServerResponseException) {
            println("Error del servidor (5xx): ${e.message}")
            Result.failure(AppError.ServerError("Error del servidor."))
        } catch (e: IOException) {
            println("Error de red al obtener los elementos: ${e.message}")
            Result.failure(AppError.NetworkError())
        } catch (e: Exception) {
            println("Error desconocido al obtener los elementos: ${e.message}")
            Result.failure(AppError.UnknownError())
        }
    }


    override suspend fun getDetalleElemento(id: String): Result<Elemento> {
        return try {
            val detalleDto = apiService.getElementosDetalleDto(id)


            val elementoDetalle = detalleDto.toDomain()

            Result.success(elementoDetalle)

        }catch (e: RedirectResponseException) {
            println("Error del cliente (redirección): ${e.message}")
            Result.failure(AppError.ServerError("Error de redirección."))
        } catch (e: ClientRequestException) {
            println("Error del cliente (4xx): ${e.message}")
            Result.failure(AppError.ServerError("Error de cliente."))
        } catch (e: ServerResponseException) {
            println("Error del servidor (5xx): ${e.message}")
            Result.failure(AppError.ServerError("Error del servidor."))
        } catch (e: IOException) {
            println("Error de red al obtener los elementos: ${e.message}")
            Result.failure(AppError.NetworkError())
        } catch (e: Exception) {
            println("Error desconocido al obtener los elementos: ${e.message}")
            Result.failure(AppError.UnknownError())
        }
    }


    //futura implementacion BBDD
    override suspend fun addFavorito(id: String) {
        //favoritosIds.value = favoritosIds.value + id
        favoritoDAO.addFavorito(FavoritoEntidad(id = id))
    }

    override suspend fun removeFavorito(id: String) {
        //favoritosIds.value = favoritosIds.value - id
        favoritoDAO.removeFavorito(id)

    }

    override fun getFavoritos(): Flow<List<Elemento>> {

        return favoritoDAO.getFavoritos()
            .flatMapLatest { entidadesFavoritas ->
                if (entidadesFavoritas.isEmpty()) {
                    flow { emit(emptyList()) }
                } else {
                    flow {
                        coroutineScope {
                            val favoritosCompletos = entidadesFavoritas.map { entidad ->
                                async { getDetalleElemento(entidad.id) }
                            }.awaitAll().mapNotNull { it.getOrNull() }

                            emit(favoritosCompletos)
                        }
                    }
                }
            }
    }
}




