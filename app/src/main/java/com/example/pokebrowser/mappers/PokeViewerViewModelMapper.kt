package com.example.pokebrowser.mappers

import androidx.navigation.NavController
import com.example.pokebrowser.viewModels.MainActivityViewModel
import com.example.pokebrowser.viewModels.PokeViewerViewModel

class PokeViewerViewModelMapper {
    fun map(
        mainActivityVm: MainActivityViewModel,
        pokeName: String,
        navController: NavController,
    ): PokeViewerViewModel {
        val pokemonUrl = mainActivityVm.pokemonList.first { p -> p.name == pokeName }

        return PokeViewerViewModel(pokemonUrl.url, navController)
    }
}