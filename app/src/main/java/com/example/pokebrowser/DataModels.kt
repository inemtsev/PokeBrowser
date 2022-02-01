package com.example.pokebrowser

data class PokemonSummary(val name: String, val imageUrl: String, val types: List<String>)

data class Pokemon(
    val types: List<String>,
    val abilities: List<String>,
    val baseStats: List<PokemonStat>,
    val name: String,
    val imageUrl: String,
    val height: Int,
    val weight: Int
)

data class PokemonStat(val statName: String, val statNumber: Int)
