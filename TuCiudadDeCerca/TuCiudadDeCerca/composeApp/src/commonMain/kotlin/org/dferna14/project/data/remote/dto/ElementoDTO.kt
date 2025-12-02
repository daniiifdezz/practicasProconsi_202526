package org.dferna14.project.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.dferna14.project.domain.model.Elemento
import kotlin.String
@Serializable
data class ElementoDTO(
    val idFicha: Int,

    val nombre: String,

    val descripcionCorta: String,

    val urlImagen: String?,

    val fechaInicio: String?,

    val fechaFin: String?,

    val horaInicio: String?,

    val horaFin: String?,

    val latitud: Double?,

    val longitud: Double?,

    val distanciaUsuarioMetros: Double?,

    val tipoFicha: String?,

    val orden: Int

)


/*
Clase envoltorio que representa la estructura completa del JSON
Objeto que contiene la lista de los ElementoDTO.
 */
@Serializable
data class ApiResult(
    //categorias de momento no es necesario, se ingora con la serializacion
    val fichas: List<ElementoDTO>
)