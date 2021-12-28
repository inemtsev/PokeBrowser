package com.example.pokebrowser.viewModels

import GetPokemonDataResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val _pokemonData = MutableLiveData<Pokemon>()
    val pokemonData = _pokemonData as LiveData<Pokemon>

    init {
        val pokeClient = PokeClient()
        val pokeClientMapper = PokeClientResponseMapper()

        viewModelScope.launch {
            val request = async { pokeClient.getPokemonData(pokeUrl) }
            val pokemonDataResponse: GetPokemonDataResponse? = request.await()

            _pokemonData.postValue(pokeClientMapper.mapResponseToPokeDataModel(pokemonDataResponse))
        }
    }
}