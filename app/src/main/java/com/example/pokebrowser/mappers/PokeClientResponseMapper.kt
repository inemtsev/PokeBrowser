package com.example.pokebrowser.mappers

import com.example.pokebrowser.pokeClient.GetPokemonDataResponse
import com.example.pokebrowser.Pokemon
import com.example.pokebrowser.PokemonSummary

interface PokeClientResponseMapper {
    fun mapPokeDataResponseToPokeSummaryModel(
        pokeDataResponse: List<GetPokemonDataResponse?>
    ): List<PokemonSummary>

    fun mapResponseToPokeDataModel(
        response: GetPokemonDataResponse?
    ): Pokemon
}