package com.example.pokebrowser.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.pokebrowser.PokemonSummary
import com.example.pokebrowser.mappers.PokeClientResponseMapper
import com.example.pokebrowser.pokeClient.GetPokemonListResponse
import com.example.pokebrowser.pokeClient.PokeClient
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class PokeBrowserViewModel(
    val pokemonList: List<GetPokemonListResponse.PokemonUrl>,
    val navController: NavController
) : ViewModel() {
    private val POKE_BROWSER_PAGE_SIZE = 10
    private val pokeClient = PokeClient()
    private val pokeClientMapper = PokeClientResponseMapper()

    private val _pokemonDataList: MutableLiveData<List<PokemonSummary>> = MutableLiveData(ArrayList())
    val pokemonDataList: LiveData<List<PokemonSummary>> = _pokemonDataList

    private var currentPokemonPage: Int = 1

    init {
        val initPokeList = pokemonList.take(POKE_BROWSER_PAGE_SIZE)
        getPokemonData(initPokeList)
    }

    fun loadPokemonData(): Unit {
        val nextPokeList = pokemonList
            .drop(currentPokemonPage * POKE_BROWSER_PAGE_SIZE)
            .take(POKE_BROWSER_PAGE_SIZE)

        getPokemonData(nextPokeList)
        currentPokemonPage++
    }

    private fun getPokemonData(nextPokeList: List<GetPokemonListResponse.PokemonUrl>) {
        viewModelScope.launch {
            val requests = nextPokeList.map { urlData ->
                async {
                    pokeClient.getPokemonData(urlData.url)
                }
            }

            val responses = requests.awaitAll()

            val oldPokemonData = _pokemonDataList.value as ArrayList
            val newPokemonData = ArrayList<PokemonSummary>(oldPokemonData.size + responses.size)
            newPokemonData.addAll(oldPokemonData.toList())
            newPokemonData.addAll(pokeClientMapper.mapPokeDataResponseToPokeSummaryModel(responses))
            _pokemonDataList.postValue(newPokemonData)
        }
    }
}