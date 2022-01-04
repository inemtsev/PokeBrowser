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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokebrowser.composable.*
import com.example.pokebrowser.ui.theme.PokeBrowserTheme
import com.example.pokebrowser.viewModels.MainActivityViewModel
import com.example.pokebrowser.viewModels.PokeBrowserViewModel
import com.example.pokebrowser.viewModels.PokeSearchViewModel
import com.example.pokebrowser.viewModels.PokeViewerViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : ComponentActivity() {
    private val vm: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        startKoin{
            androidLogger()
            androidContext(this@MainActivity)
            modules(appDependencies)
        }

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
    val pokeViewerVm = viewModel<PokeViewerViewModel>()

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
                val pokeBrowserVm = viewModel<PokeBrowserViewModel>()
                PokeBrowser(model = pokeBrowserVm, nav = navController)

            }
            composable(route = "view-screen/{pokeName}") { backStackEntry ->
                pokeViewerVm.setPokemonName(backStackEntry.arguments?.getString("pokeName") ?: "ditto")
                PokeViewer(model = pokeViewerVm)
            }
            composable(route = "search-screen") {
                val pokeSearchVm = viewModel<PokeSearchViewModel>()
                pokeViewerVm.setPokemonName("")
                pokeViewerVm.setLoadingState(true)
                PokeSearch(model = pokeSearchVm, viewerVm = pokeViewerVm)
            }
        }
    }
}