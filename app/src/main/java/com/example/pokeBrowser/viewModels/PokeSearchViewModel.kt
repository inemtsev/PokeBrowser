package com.example.pokeBrowser.viewModels

import androidx.lifecycle.ViewModel

class PokeSearchViewModel : ViewModel() {
    fun onSearchClick(pokeViewerVm: PokeViewerViewModel, searchInput: String) {
        if(searchInput.isNotEmpty()) {
            pokeViewerVm.setPokemonName(searchInput)
            pokeViewerVm.getPokemonData()
        }
    }
}