package org.dferna14.project.data.local

import androidx.room.RoomDatabase


expect fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>



//uso de singelton, y lazy para que la bbdd solo se construya cuando se accede a ella
val database: AppDatabase by lazy {
    getDatabaseBuilder().build()
}