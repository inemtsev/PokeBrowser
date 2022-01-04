package com.example.pokebrowser.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokebrowser.PokemonSummary
import com.example.pokebrowser.mappers.PokeClientResponseMapper
import com.example.pokebrowser.pokeClient.GetPokemonListResponse
import com.example.pokebrowser.repositories.PokeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class PokeBrowserViewModel : ViewModel() {
    private val POKE_BROWSER_PAGE_SIZE = 10
    private val _pokeRepo: PokeRepository by inject(PokeRepository::class.java)
    private val _pokeClientMapper by inject<PokeClientResponseMapper>(PokeClientResponseMapper::class.java)

    private var _pokemonList: Sequence<GetPokemonListResponse.PokemonUrl> = sequenceOf()
    private val _pokemonDataList: MutableLiveData<List<PokemonSummary>> = MutableLiveData(ArrayList())
    val pokemonDataList: LiveData<List<PokemonSummary>> = _pokemonDataList

    private var currentPokemonPage: Int = 1

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val pokeListRequest = async { _pokeRepo.getPokemonList() }
            val pokeListResponse = pokeListRequest.await()
            _pokemonList = pokeListResponse.results.asSequence()

            val initPokeList = _pokemonList.take(POKE_BROWSER_PAGE_SIZE)
            getPokemonData(initPokeList)
        }

    }

    fun loadPokemonData(): Unit {
        val nextPokeList = _pokemonList
            .drop(currentPokemonPage * POKE_BROWSER_PAGE_SIZE)
            .take(POKE_BROWSER_PAGE_SIZE)

        getPokemonData(nextPokeList)
        currentPokemonPage++
    }

    private fun getPokemonData(nextPokeList: Sequence<GetPokemonListResponse.PokemonUrl>) {
        viewModelScope.launch(Dispatchers.IO) {
            val requests = nextPokeList.map { urlData ->
                async {
                    _pokeRepo.getPokemonData(urlData.url)
                }
            }.toList()

            val responses = requests.awaitAll()

            val oldPokemonData = _pokemonDataList.value as ArrayList
            val newPokemonData = ArrayList<PokemonSummary>(oldPokemonData.size + responses.size)
            newPokemonData.addAll(oldPokemonData.toList())
            newPokemonData.addAll(_pokeClientMapper.mapPokeDataResponseToPokeSummaryModel(responses))
            _pokemonDataList.postValue(newPokemonData)
        }
    }
}