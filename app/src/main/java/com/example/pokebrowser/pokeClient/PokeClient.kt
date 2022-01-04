package com.example.pokebrowser.pokeClient

interface PokeClient {
    suspend fun getPokemonList(number: Int, offset: Int): GetPokemonListResponse?
    suspend fun getPokemonData(pokemonUrl: String): GetPokemonDataResponse?
}