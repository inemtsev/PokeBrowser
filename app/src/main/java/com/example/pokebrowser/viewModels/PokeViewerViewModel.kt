package com.example.pokebrowser.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokebrowser.Pokemon
import com.example.pokebrowser.mappers.PokeClientResponseMapper
import com.example.pokebrowser.repositories.PokeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

class PokeViewerViewModel : ViewModel() {
    private val _pokeRepo by KoinJavaComponent.inject<PokeRepository>(PokeRepository::class.java)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading as LiveData<Boolean>

    private val _pokemonData = MutableLiveData<Pokemon>()
    val pokemonData = _pokemonData as LiveData<Pokemon>

    private var _pokeName: String = ""

    fun getPokemonData() {
        if (_pokeName.isNotEmpty()) {
            _isLoading.postValue(true)
            viewModelScope.launch {
                val pokeUrl = _pokeRepo.getPokemonUrl(_pokeName)
                if(pokeUrl.isNotEmpty()) {
                    val pokeDataRequest = async { _pokeRepo.getPokemonData(pokeUrl) }
                    val pokeDataResult = pokeDataRequest.await()

                    val pokeClientMapper = PokeClientResponseMapper()
                    _pokemonData.postValue(pokeClientMapper.mapResponseToPokeDataModel(pokeDataResult))
                    _isLoading.postValue(false)
                }
            }
        }
    }

    fun setPokemonName(pokeName: String) {
        _pokeName = pokeName
    }

    fun setLoadingState(isLoading: Boolean) {
        _isLoading.postValue(isLoading)
    }
}