package com.example.pokebrowser.mappers

import androidx.navigation.NavController
import com.example.pokebrowser.viewModels.MainActivityViewModel
import com.example.pokebrowser.viewModels.PokeSearchViewModel

class PokeSearchViewModelMapper {
    fun map(
        mainActivityVm: MainActivityViewModel,
        navController: NavController
    ): PokeSearchViewModel {
        return PokeSearchViewModel(mainActivityVm.pokemonList, navController)
    }
}