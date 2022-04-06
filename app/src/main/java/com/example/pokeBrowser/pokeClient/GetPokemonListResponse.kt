package com.example.pokeBrowser.pokeClient

data class GetPokemonListResponse(
    val count: Int,
    @Transient
    val next: String? = null,
    @Transient
    val previous: String? = null,
    val results: List<PokemonUrl>
) {
    data class PokemonUrl(
        val name: String,
        val url: String
    )
}



