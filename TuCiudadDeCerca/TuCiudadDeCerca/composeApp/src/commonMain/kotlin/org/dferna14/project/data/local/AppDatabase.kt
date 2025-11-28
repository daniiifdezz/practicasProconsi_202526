package org.dferna14.project.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

//dar una vuelta a esto, migraciones, versionado
@Database(
    entities = [FavoritoEntidad::class],
    version = 1
)


abstract class AppDatabase : RoomDatabase() {

    abstract fun favoritoDAO(): FavoritoDAO
}


