package com.example.pokebrowser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokebrowser.pokeClient.PokeClient
import kotlinx.coroutines.launch

const val MaxNumberOfPokemon = 1118
const val PageSize = 10

class MainActivityViewModel : ViewModel() {
    private val pokeClient = PokeClient()
    private val pokemonDataList: MutableLiveData<List<Pokemon>> = MutableLiveData()
    private val _isLoadingInit: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val isLoadingInit: LiveData<Boolean> = _isLoadingInit

    var page: Int = 1

    lateinit var pokemonList: Map<String,String>

    fun getPokemonList(): Unit {
        viewModelScope.launch {
            _isLoadingInit.postValue(true)
            val response = pokeClient.getPokemonList(MaxNumberOfPokemon, 0)

            if(response != null){
                pokemonList = response.results.associateBy({it.name}, {it.url})
                _isLoadingInit.postValue(false)
            } else {
                // log this error later
            }
        }
    }

    fun loadPokemonData(): Unit {
        viewModelScope.launch {

        }
    }

    fun incrementPage(): Int {
        return ++page
    }

}