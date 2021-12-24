package com.example.pokebrowser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokebrowser.composable.NavigationBar
import com.example.pokebrowser.composable.PokeBrowser
import com.example.pokebrowser.composable.PokeViewer
import com.example.pokebrowser.composable.SplashScreen
import com.example.pokebrowser.mappers.PokeBrowserViewModelMapper
import com.example.pokebrowser.mappers.PokeViewerViewModelMapper
import com.example.pokebrowser.ui.theme.PokeBrowserTheme
import com.example.pokebrowser.viewModels.MainActivityViewModel

class MainActivity : ComponentActivity() {
    private val vm: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        vm.getPokemonList()
        super.onCreate(savedInstanceState)
        setContent {
            PokeBrowserTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Navigator(
                        vm = vm,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Navigator(vm: MainActivityViewModel, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val isInitLoading by vm.isLoadingInit.observeAsState()
    val pokeBrowserMapper by lazy { PokeBrowserViewModelMapper() }
    val pokeViewerMapper by lazy { PokeViewerViewModelMapper() }

    Column(verticalArrangement = Arrangement.Top, modifier = modifier) {
        NavigationBar(
            navController = navController,
            isInitLoading = isInitLoading,
            modifier = Modifier
                .height(50.dp)
                .padding(vertical = 10.dp, horizontal = 4.dp)
        )
        NavHost(navController = navController, startDestination = "splash-screen") {
            composable(route = "splash-screen") {
                SplashScreen(
                    navController = navController,
                    isLoadingInit = isInitLoading
                )
            }
            composable(route = "explore-screen") {
                PokeBrowser(pokeBrowserMapper.map(vm, navController))
            }
            composable(route = "view-screen/{pokeName}") { backStackEntry ->
                PokeViewer(
                    pokeViewerMapper.map(
                        mainVm = vm,
                        pokeName = backStackEntry.arguments?.getString("pokeName") ?: "ditto",
                        navController = navController
                    )
                )
            }
        }
    }
}