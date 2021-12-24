package com.example.pokebrowser.mappers

import androidx.navigation.NavController
import com.example.pokebrowser.viewModels.MainActivityViewModel
import com.example.pokebrowser.viewModels.PokeViewerViewModel

class PokeViewerViewModelMapper {
    fun map(
        mainVm: MainActivityViewModel,
        pokeName: String,
        navController: NavController,
    ): PokeViewerViewModel {
        val pokemonUrl = mainVm.pokemonList.first { p -> p.name == pokeName }

        return PokeViewerViewModel(pokemonUrl.url, navController)
    }
}