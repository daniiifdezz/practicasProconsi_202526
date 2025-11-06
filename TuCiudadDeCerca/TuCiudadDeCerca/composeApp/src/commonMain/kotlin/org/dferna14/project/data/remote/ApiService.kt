package org.dferna14.project.data.remote

import org.dferna14.project.data.remote.dto.ApiResult
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.dferna14.project.data.remote.dto.ElementoDetalleDTO


class ApiService(private val httpClient: HttpClient) {

    private val baseUrl = "https://tuciudaddecerca-api.proconsi.com"

    // Esta función SÓLO se encarga de la llamada de red. Devuelve el DTO crudo.
    suspend fun getElementosDto(): ApiResult {
        val url = "$baseUrl/Categoria?idCategoriaPadre=30&idIdioma=0&idProyecto=1"
        return httpClient.get(url).body<ApiResult>()
    }

    suspend fun getElementosDetalleDto(id: String): ElementoDetalleDTO {
        val url = "$baseUrl/Ficha?idFicha=$id&TipoFicha=F&idIdioma=0&idProyecto=1"
        println("URL final de la API: $url")
        return httpClient.get(url).body()
    }


}