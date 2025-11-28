package org.dferna14.project.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.dferna14.project.domain.model.Elemento
import kotlin.String
//no es necesario el serialName
@Serializable
data class ElementoDTO(
    @SerialName("idFicha")
    val idFicha: Int,

    @SerialName("nombre")
    val nombre: String,

    @SerialName("descripcionCorta")
    val descripcionCorta: String,

    @SerialName("urlImagen")
    val urlImagen: String?,

    @SerialName("fechaInicio")
    val fechaInicio: String?,

    @SerialName("fechaFin")
    val fechaFin: String?,

    @SerialName("horaInicio")
    val horaInicio: String?,

    @SerialName("horaFin")
    val horaFin: String?,

    @SerialName("latitud")
    val latitud: Double?,

    @SerialName("longitud")
    val longitud: Double?,

    @SerialName("distanciaUsuarioMetros")
    val distanciaUsuarioMetros: Double?,

    @SerialName("tipoFicha")
    val tipoFicha: String?,

    @SerialName("orden")
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