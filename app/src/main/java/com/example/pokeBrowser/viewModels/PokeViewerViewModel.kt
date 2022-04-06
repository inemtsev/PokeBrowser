package com.example.pokeBrowser.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeBrowser.Pokemon
import com.example.pokeBrowser.mappers.PokeClientResponseMapper
import com.example.pokeBrowser.repositories.PokeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PokeViewerViewModel(
    val pokeRepo: PokeRepository,
    val pokeClientMapper: PokeClientResponseMapper,
    val teamMemberAddViewModel: TeamMemberAddViewModel
    ) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading as LiveData<Boolean>

    private val _pokemonData = MutableLiveData<Pokemon>()
    val pokemonData = _pokemonData as LiveData<Pokemon>

    private var _pokeName: String = ""

    fun getPokemonData() {
        if (_pokeName.isNotEmpty()) {
            val pokeUrl = pokeRepo.getPokemonUrl(_pokeName)

            if (pokeUrl.isNotEmpty()) {
                viewModelScope.launch(Dispatchers.IO) {
                    _isLoading.postValue(true)
                    val pokeDataRequest = async { pokeRepo.getPokemonData(pokeUrl) }
                    val pokeDataResult = pokeDataRequest.await()

                    _pokemonData.postValue(
                        pokeClientMapper.mapResponseToPokeDataModel(pokeDataResult)
                    )
                    _isLoading.postValue(false)
                }
            }
        }
    }

    fun setupAddPokemon() {
        teamMemberAddViewModel.loadTeams()
    }

    fun setPokemonName(pokeName: String) {
        _pokeName = pokeName
    }

    fun setLoadingState(isLoading: Boolean) {
        _isLoading.postValue(isLoading)
    }
}