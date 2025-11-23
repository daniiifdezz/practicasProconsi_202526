package org.dferna14.project.data.remote.dto
import kotlinx.serialization.Serializable

@Serializable
data class ElementoDetalleDTO(
    val idFicha: Int,
    val nombre: String,
    val descripcionCorta: String?,
    val descripcion: String?,
    val latitud: Double?,
    val longitud: Double?,
    val direccion: String?,
    val email: String?,
    val telefono: String?,
    val urlImagen: String?,
    val media: MediaDTO?,
    val subFichas: List<SubFichaDTO> = emptyList(),
    val rutas: List<RutaDTO> = emptyList()
)

@Serializable
data class MediaDTO(
    val images: List<String> = emptyList(),
    val audios: List<String> = emptyList(),
    val videos: List<String> = emptyList()
)

@Serializable
data class SubFichaDTO(
    val nombre: String,
    val descripcion: String?
)

@Serializable
data class RutaDTO(
    val nombre: String,
    val descripcion: String?
)