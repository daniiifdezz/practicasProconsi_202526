package org.dferna14.project.data.local


import androidx.room.Room
import androidx.room.RoomDatabase
import org.dferna14.project.AppContext


actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val appContext = AppContext.get().applicationContext
    val dbFile = appContext.getDatabasePath("app_database.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}