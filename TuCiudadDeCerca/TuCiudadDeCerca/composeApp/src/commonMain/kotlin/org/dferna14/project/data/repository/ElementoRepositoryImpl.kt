package org.dferna14.project.data.repository

import kotlinx.coroutines.delay
import org.dferna14.project.domain.model.Elemento
import org.dferna14.project.domain.repository.ElementoRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.dferna14.project.data.remote.dto.ApiResult
import org.dferna14.project.data.remote.dto.ElementoDTO
import org.dferna14.project.data.remote.ktorClient
import org.dferna14.project.data.remote.dto.toDomain


/*
Devolvemos elementos simulados, aqui entiendo que ira KTOR
El VM recibe la llista y actualiza su uiState
 */
class ElementoRepositoryImpl(private val httpClient: HttpClient = ktorClient) : ElementoRepository {

    private val url = "https://tuciudaddecerca-api.proconsi.com/Categoria?idCategoriaPadre=30&idIdioma=0&idProyecto=1"

    override suspend fun getElementos(): List<Elemento> {
        delay(2000)
        return try{
            //aqui esperamos al objeto
            val response = httpClient.get(url).body<ApiResult>()

            //sacamos la lista del objeto
            val dtos = response.fichas

            //convertimos la lista de DTO a lista de elementos de nuestro dominio
            dtos.map {it.toDomain()}

        }catch (e: Exception){
            println("Error al obtener los elementos del JSON ${e.message}")
            emptyList()
        }
    }


}