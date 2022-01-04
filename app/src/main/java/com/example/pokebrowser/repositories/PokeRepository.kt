package com.example.pokebrowser.repositories

import com.example.pokebrowser.pokeClient.GetPokemonDataResponse
import com.example.pokebrowser.pokeClient.GetPokemonListResponse

interface PokeRepository {
    suspend fun getPokemonList(): GetPokemonListResponse
    fun getPokemonUrl(pokeName: String): String
    suspend fun getPokemonData(url: String): GetPokemonDataResponse
}