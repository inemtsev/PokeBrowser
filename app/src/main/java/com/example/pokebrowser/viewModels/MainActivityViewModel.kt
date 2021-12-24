package com.example.pokebrowser.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokebrowser.pokeClient.GetPokemonListResponse
import com.example.pokebrowser.pokeClient.PokeClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    val maxNumberOfPokemon = 1118
    private val pokeClient = PokeClient()

    private val _isLoadingInit: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val isLoadingInit: LiveData<Boolean> = _isLoadingInit

    lateinit var pokemonList: List<GetPokemonListResponse.PokemonUrl>

    fun getPokemonList(): Unit {
        viewModelScope.launch {
            _isLoadingInit.postValue(true)
            val response = pokeClient.getPokemonList(maxNumberOfPokemon, 0)

            if(response != null){
                pokemonList = response.results
                delay(1000)
                _isLoadingInit.postValue(false)
            } else {
                // log this error later
            }
        }
    }
}