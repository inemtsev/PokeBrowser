package com.example.pokebrowser.mappers

import androidx.navigation.NavHostController
import com.example.pokebrowser.viewModels.MainActivityViewModel
import com.example.pokebrowser.viewModels.PokeBrowserViewModel

class PokeBrowserViewModelMapper {
    fun map(mainActivityVm: MainActivityViewModel, navController: NavHostController): PokeBrowserViewModel {
        return PokeBrowserViewModel(
            mainActivityVm.pokemonList,
            navController
        )
    }
}