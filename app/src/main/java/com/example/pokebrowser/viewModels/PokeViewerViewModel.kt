package com.example.pokebrowser.viewModels

import GetPokemonDataResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.pokebrowser.Pokemon
import com.example.pokebrowser.mappers.PokeClientResponseMapper
import com.example.pokebrowser.pokeClient.PokeClient
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PokeViewerViewModel(
    val pokeUrl: String,
    val navController: NavController
) : ViewModel() {
    var pokemonData: Pokemon

    init {
        val pokeClient = PokeClient()
        val pokeClientMapper = PokeClientResponseMapper()

        var pokemonDataResponse: GetPokemonDataResponse? = null

        viewModelScope.launch {
            val request = async { pokeClient.getPokemonData(pokeUrl) }
            pokemonDataResponse = request.await()
        }

        pokemonData = pokeClientMapper.mapResponseToPokeDataModel(pokemonDataResponse)
    }
}