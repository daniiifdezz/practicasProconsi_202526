package org.dferna14.project.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.dferna14.project.data.local.AppDatabase
import org.dferna14.project.data.remote.ApiService
import org.dferna14.project.data.remote.ktorClient
import org.dferna14.project.data.repository.ElementoRepositoryImpl
import org.dferna14.project.domain.repository.ElementoRepository
import org.dferna14.project.domain.usecase.GetElementoDetalleUseCase
import org.dferna14.project.domain.usecase.GetElementosUseCase
import org.dferna14.project.domain.usecase.GetFavoritosUseCase
import org.dferna14.project.domain.usecase.GestionarFavoritoUseCase
import org.dferna14.project.presentation.ListadoVM
import org.dferna14.project.presentation.detalle.DetalleVM
import org.dferna14.project.presentation.favoritos.FavoritosVM

expect val platformModule: Module

val appModule = module {
    includes(platformModule)

    single { ktorClient }
    single { ApiService(get()) }
    
    single { get<AppDatabase>().favoritoDAO() }

    single<ElementoRepository> { ElementoRepositoryImpl(get(), get()) }

    single { GetElementosUseCase(get()) }

    single { GetElementoDetalleUseCase(get()) }

    single { GestionarFavoritoUseCase(get()) }

    single { GetFavoritosUseCase(get()) }

    viewModel { ListadoVM(get(), get()) }

    viewModel { FavoritosVM(get()) }
    
    viewModel { params ->
        DetalleVM(
            elementoId = params.get(),
            getElementoDetalleUseCase = get(),
            gestionarFavoritoUseCase = get(),
            getFavoritosUseCase = get()
        )
    }
}
