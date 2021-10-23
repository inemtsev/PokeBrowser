package com.example.pokebrowser

data class GetPokemonListResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<PokemonUrl>
)

data class PokemonUrl(
    val name: String,
    val url: String
)