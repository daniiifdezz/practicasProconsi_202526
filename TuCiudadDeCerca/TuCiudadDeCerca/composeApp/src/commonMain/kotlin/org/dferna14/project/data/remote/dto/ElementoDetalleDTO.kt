package org.dferna14.project.data.remote.dto
import kotlinx.serialization.Serializable

@Serializable
data class ElementoDetalleDTO(
    val idFicha: Int,
    val nombre: String,
    val descripcionCorta: String,
    val descripcion: String,
    val latitud: Double?,
    val longitud: Double?,
    val direccion: String?,
    val email: String?,
    val telefono: String?,
    val urlImagen: String?,
    val media: MediaDTO?
)

@Serializable
data class MediaDTO(
    val images: List<String> = emptyList(),
    val audios: List<String> = emptyList(),
    val videos: List<String> = emptyList()
)