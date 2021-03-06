package com.example.pokeBrowser.repositories

import com.example.pokeBrowser.pokeClient.GetPokemonDataResponse
import com.example.pokeBrowser.pokeClient.GetPokemonListResponse
import com.example.pokeBrowser.pokeClient.PokeClient
import org.koin.java.KoinJavaComponent.inject

class PokeRepositoryImpl : PokeRepository {
    private val maxNumberOfPokemon = 1118
    private val _pokeClient by inject<PokeClient>(PokeClient::class.java)

    private var pokemonList: GetPokemonListResponse? = null
    private val pokeDataMap = HashMap<String, GetPokemonDataResponse>(maxNumberOfPokemon)

    override suspend fun getPokemonList(): GetPokemonListResponse {
        return if (pokemonList != null) {
            pokemonList as GetPokemonListResponse
        } else {
            val pokeListResponse = _pokeClient.getPokemonList(maxNumberOfPokemon, 0)
            if (pokeListResponse != null) {
                pokemonList = pokeListResponse
                return pokeListResponse
            } else {
                // add log here
                throw Exception("Pokemon client (LIST) returned null")
            }
        }
    }

    override fun getPokemonUrl(pokeName: String): String {
        val match = pokemonList?.results?.firstOrNull { p -> p.name == pokeName.lowercase() }
        return match?.url ?: ""
    }

    override suspend fun getPokemonData(url: String): GetPokemonDataResponse {
        if (pokeDataMap.containsKey(url)) {
            return pokeDataMap[url] as GetPokemonDataResponse
        }

        val result = _pokeClient.getPokemonData(url)
        if (result != null) {
            pokeDataMap[url] = result
            return result
        } else {
            // add log here
            throw Exception("Pokemon client (Pokemon Data) returned null")
        }

    }
}