package com.example.pokebrowser

import com.example.pokebrowser.repositories.PokeRepository
import com.example.pokebrowser.repositories.PokeRepositoryImpl
import org.koin.dsl.module

val appDependencies = module {
    single<PokeRepository> { PokeRepositoryImpl() }
}