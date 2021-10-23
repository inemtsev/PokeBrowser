package com.example.pokebrowser

import androidx.lifecycle.ViewModel

const val MaxNumberOfPokemon = 1118

class MainActivityViewModel : ViewModel() {
    private val pokeClient = PokeClient()

    var isLoading: Boolean = false
    var page: Int = 0
    lateinit var pokemonList: MutableMap<String,String>

    fun getPokemonList(): Unit {

    }

    fun incrementPage(): Int {
        return ++page
    }

}