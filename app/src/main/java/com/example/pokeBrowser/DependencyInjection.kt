package com.example.pokeBrowser

import com.example.pokeBrowser.db.TeamRepository
import com.example.pokeBrowser.db.TeamRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import com.example.pokeBrowser.mappers.PokeClientResponseMapper
import com.example.pokeBrowser.mappers.PokeClientResponseMapperImpl
import com.example.pokeBrowser.pokeClient.PokeClient
import com.example.pokeBrowser.pokeClient.PokeClientImpl
import com.example.pokeBrowser.repositories.PokeRepository
import com.example.pokeBrowser.repositories.PokeRepositoryImpl
import com.example.pokeBrowser.viewModels.PokeViewerViewModel
import com.example.pokeBrowser.viewModels.TeamMemberAddViewModel
import com.example.pokeBrowser.viewModels.TeamBuilderViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appDependencies = module {
    single<PokeClient> { PokeClientImpl(get()) }
    single<PokeRepository> { PokeRepositoryImpl() }
    single<TeamRepository> { TeamRepositoryImpl(androidContext()) }
    single<PokeClientResponseMapper> { PokeClientResponseMapperImpl() }

    viewModel{ TeamBuilderViewModel(get()) }
    viewModel{ TeamMemberAddViewModel(get()) }
    viewModel{ PokeViewerViewModel(get(), get(), get()) }
}
