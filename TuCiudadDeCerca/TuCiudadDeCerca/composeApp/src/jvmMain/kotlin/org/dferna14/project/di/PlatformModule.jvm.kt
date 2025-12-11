package org.dferna14.project.di

import org.dferna14.project.data.local.AppDatabase
import org.dferna14.project.data.local.getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<AppDatabase> { getDatabaseBuilder().build() }
}
