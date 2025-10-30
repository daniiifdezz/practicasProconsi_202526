package org.dferna14.project.data.mapper

import org.dferna14.project.data.remote.dto.ElementoDTO
import org.dferna14.project.data.remote.dto.ElementoDetalleDTO
import org.dferna14.project.domain.model.Elemento


/*
Convertir datos de red a nuestro modelo de dominio de Elemnto
 */


fun ElementoDTO.toDomain(): Elemento {
    return Elemento(
        id = this.idFicha.toString(),
        nombre = this.nombre,
        descripcionCorta = this.descripcionCorta,
        fechaInicio = this.fechaInicio,
        fechaFin = this.fechaFin,
        horaInicio = this.horaInicio,
        horaFin = this.horaFin,
        latitud = this.latitud,
        longitud = this.longitud,
        urlImagen = this.urlImagen,
        distanciaUsuarioMetros = this.distanciaUsuarioMetros,
        tipoFicha = this.tipoFicha,
        orden = this.orden,
        //galeriaImagenes = emptyList()
    )
}

fun ElementoDetalleDTO.toDomain(): Elemento {
    // Primero, necesitamos actualizar el modelo de dominio para que acepte la nueva información.
    // Ve a 'domain/model/Elemento.kt' y añade 'descripcionLarga' y 'galeriaImagenes'.

    return Elemento(
        id = this.idFicha.toString(),
        nombre = this.nombre,
        descripcionCorta = this.descripcion, // Usamos la descripción larga como corta por ahora
        descripcionLarga = this.descripcion, // Nuevo campo
        urlImagen = this.urlImagen,
        galeriaImagenes = this.media?.images ?: emptyList(), // Nuevo campo
        latitud = this.latitud,
        longitud = this.longitud,
        fechaInicio = null,
        fechaFin = null,
        horaInicio = null,
        horaFin = null,
        distanciaUsuarioMetros = null,
        tipoFicha = null,
        orden = 0
    )
}