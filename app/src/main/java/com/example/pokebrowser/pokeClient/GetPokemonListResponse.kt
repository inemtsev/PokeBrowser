package com.example.pokebrowser.pokeClient

data class GetPokemonListResponse(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: List<PokemonUrl>
) {
    data class PokemonUrl(
        val name: String,
        val url: String
    )
}



