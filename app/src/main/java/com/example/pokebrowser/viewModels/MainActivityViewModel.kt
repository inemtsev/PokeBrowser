package com.example.pokebrowser.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokebrowser.repositories.PokeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

class MainActivityViewModel : ViewModel() {
    private val pokeRepo: PokeRepository by KoinJavaComponent.inject(PokeRepository::class.java)

    private val _isLoadingInit: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val isLoadingInit: LiveData<Boolean> = _isLoadingInit

    fun getPokemonList(): Unit {
        viewModelScope.launch {
            _isLoadingInit.postValue(true)
            val response = pokeRepo.getPokemonList()

            if(response != null){
                delay(500)
                _isLoadingInit.postValue(false)
            } else {
                // log this error
            }
        }
    }
}