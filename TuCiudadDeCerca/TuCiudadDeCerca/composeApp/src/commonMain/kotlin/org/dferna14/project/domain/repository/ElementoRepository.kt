package org.dferna14.project.domain.repository

import org.dferna14.project.domain.model.Elemento


interface ElementoRepository{
    suspend fun getElementos(): List<Elemento>
}