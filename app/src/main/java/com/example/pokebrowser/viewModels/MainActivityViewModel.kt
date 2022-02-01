package com.example.pokebrowser.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokebrowser.repositories.PokeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class MainActivityViewModel : ViewModel() {
    private val pokeRepo: PokeRepository by inject(PokeRepository::class.java)

    private val _isLoadingInit: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoadingInit: LiveData<Boolean> = _isLoadingInit

    private val _topBarTitle: MutableLiveData<String> = MutableLiveData("Explore")
    val topBarTitle: LiveData<String> = _topBarTitle

    fun updateTopBarTitle(newTitle: String) {
        _topBarTitle.postValue(newTitle)
    }

    fun getPokemonList(): Unit {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingInit.postValue(true)

            val request = async { pokeRepo.getPokemonList() }
            request.await()
            delay(500)

            _isLoadingInit.postValue(false)
        }
    }
}