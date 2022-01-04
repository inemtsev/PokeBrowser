package com.example.pokebrowser

import com.example.pokebrowser.mappers.PokeClientResponseMapper
import com.example.pokebrowser.mappers.PokeClientResponseMapperImpl
import com.example.pokebrowser.pokeClient.PokeClient
import com.example.pokebrowser.pokeClient.PokeClientImpl
import com.example.pokebrowser.repositories.PokeRepository
import com.example.pokebrowser.repositories.PokeRepositoryImpl
import org.koin.dsl.module

val appDependencies = module {
    single<PokeClient> { PokeClientImpl(get()) }
    single<PokeRepository> { PokeRepositoryImpl() }
    single<PokeClientResponseMapper> { PokeClientResponseMapperImpl() }
}