package com.example.pokebrowser.viewModels

import androidx.lifecycle.ViewModel

class PokeSearchViewModel : ViewModel() {
    fun onSearchClick(pokeViewerVm: PokeViewerViewModel, searchInput: String) {
        if(searchInput.isNotEmpty()) {
            pokeViewerVm.setPokemonName(searchInput)
            pokeViewerVm.getPokemonData()
        }
    }
}