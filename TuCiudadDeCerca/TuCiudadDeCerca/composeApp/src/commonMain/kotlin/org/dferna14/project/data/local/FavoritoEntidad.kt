package org.dferna14.project.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

//pintamos la tabla de favoritos
@Entity(tableName = "favoritos")
data class FavoritoEntidad(

    @PrimaryKey
    val id: String
)

