package org.dferna14.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Elemento(

    val id: String,
    val nombre: String,
    val descripcionCorta: String,
    val fechaInicio: String?,
    val fechaFin: String?,
    val horaInicio: String?,
    val horaFin: String?,
    val latitud: Double?,
    val longitud: Double?,
    val urlImagen: String?,
    val distanciaUsuarioMetros: Double?,
    val tipoFicha: String?,
    val orden: Int,
    val imageUrl: String? = null,
    val descripcionLarga: String? = null,
    val galeriaImagenes: List<String>? = emptyList(),

)