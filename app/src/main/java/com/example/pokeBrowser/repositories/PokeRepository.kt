package com.example.pokeBrowser.repositories

import com.example.pokeBrowser.pokeClient.GetPokemonDataResponse
import com.example.pokeBrowser.pokeClient.GetPokemonListResponse

interface PokeRepository {
    suspend fun getPokemonList(): GetPokemonListResponse
    fun getPokemonUrl(pokeName: String): String
    suspend fun getPokemonData(url: String): GetPokemonDataResponse
}