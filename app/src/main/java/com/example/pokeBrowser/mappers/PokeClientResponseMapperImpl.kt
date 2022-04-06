package com.example.pokeBrowser.mappers

import com.example.pokeBrowser.pokeClient.GetPokemonDataResponse
import com.example.pokeBrowser.Pokemon
import com.example.pokeBrowser.PokemonStat
import com.example.pokeBrowser.PokemonSummary

class PokeClientResponseMapperImpl : PokeClientResponseMapper {
    override fun mapPokeDataResponseToPokeSummaryModel(
        pokeDataResponse: List<GetPokemonDataResponse?>
    ): List<PokemonSummary> {
        return pokeDataResponse.mapNotNull { p ->
            p?.let {
                PokemonSummary(
                    name = p.name,
                    imageUrl = p.sprites.other.officialArtwork.frontDefault,
                    types = p.types.map { t -> t.type.name }
                )
            }
        }
    }

    override fun mapResponseToPokeDataModel(
        response: GetPokemonDataResponse?
    ): Pokemon {
        if (response == null) {
            throw Exception("Client returned null for Pokemon data!")
        }

        return Pokemon(
            types = response.types.map { t -> t.type.name },
            abilities = response.abilities.map { a -> a.ability.name },
            baseStats = response.stats.map { s -> PokemonStat(s.stat.name, s.baseStat) },
            height = response.height,
            weight = response.weight,
            imageUrl = response.sprites.other.officialArtwork.frontDefault,
            name = response.name
        )
    }
}