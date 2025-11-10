package org.dferna14.project.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

//interacciones con la bbdd
@Dao
interface FavoritoDAO {

    //replace para ids duplicados
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorito(favorito: FavoritoEntidad)


    @Query("DELETE FROM favoritos WHERE id = :id")
    suspend fun removeFavorito(id: String)


    @Query("SELECT * FROM favoritos")
    fun getFavoritos(): Flow<List<FavoritoEntidad>>
}