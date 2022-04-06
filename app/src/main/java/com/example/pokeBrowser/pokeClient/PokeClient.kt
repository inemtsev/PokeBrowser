package com.example.pokeBrowser.pokeClient

interface PokeClient {
    suspend fun getPokemonList(number: Int, offset: Int): GetPokemonListResponse?
    suspend fun getPokemonData(pokemonUrl: String): GetPokemonDataResponse?
}