package com.example.pokeBrowser.mappers

import com.example.pokeBrowser.pokeClient.GetPokemonDataResponse
import com.example.pokeBrowser.Pokemon
import com.example.pokeBrowser.PokemonSummary

interface PokeClientResponseMapper {
    fun mapPokeDataResponseToPokeSummaryModel(
        pokeDataResponse: List<GetPokemonDataResponse?>
    ): List<PokemonSummary>

    fun mapResponseToPokeDataModel(
        response: GetPokemonDataResponse?
    ): Pokemon
}