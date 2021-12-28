package com.example.pokebrowser.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.pokebrowser.pokeClient.GetPokemonListResponse

class PokeSearchViewModel(
        val pokeList: List<GetPokemonListResponse.PokemonUrl>,
        val navController: NavController
    ) : ViewModel() {

    private val _pokeViewerVm = MutableLiveData<PokeViewerViewModel?>()
    val pokeViewerVm = _pokeViewerVm as LiveData<PokeViewerViewModel?>

    fun onSearchClick(searchInput: String) {
        if(searchInput.isNotEmpty()) {
            val pokeUrl = pokeList.firstOrNull() { p -> p.name == searchInput.lowercase() }
            if(pokeUrl != null) {
                _pokeViewerVm.postValue(PokeViewerViewModel(pokeUrl.url, navController))
            }
        }
    }
}