package com.example.pokebrowser

class Pokemon(val name: String, val image: String, private val types: List<String>) {
    companion object {
        val pokemonTypes = mapOf(
            "bug" to "bug.png",
            "dark" to "dark.png",
            "dragon" to "dragon.png",
            "electric" to "electric.png",
            "fairy" to "fairy.png",
            "fighting" to "fighting.png",
            "fire" to "fire.png",
            "flying" to "flying.png",
            "ghost" to "ghost.png",
            "grass" to "grass.png",
            "ground" to "ground.png",
            "ice" to "ice.png",
            "normal" to "normal.png",
            "poison" to "poison.png",
            "psychic" to "psychic.png",
            "rock" to "rock.png",
            "steel" to "steel.png",
            "water" to "water.png",
        )
    }

    fun getTypeBitmaps(): List<String> {
        return types.mapNotNull { t ->
            pokemonTypes[t]
        }
    }
}