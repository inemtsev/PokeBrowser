package com.example.pokebrowser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokebrowser.composable.PokeBrowser
import com.example.pokebrowser.composable.PokeViewer
import com.example.pokebrowser.composable.SplashScreen
import com.example.pokebrowser.ui.theme.PokeBrowserTheme

class MainActivity : ComponentActivity() {
    private val vm: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        vm.getPokemonList()
        super.onCreate(savedInstanceState)
        setContent {
            PokeBrowserTheme {
                Text(text = "Gotta catch'em all!", Modifier.padding(8.dp))
                Surface(color = MaterialTheme.colors.background) {
                    Navigation(vm)
                }
            }
        }
    }
}

@Composable
fun Navigation(vm: MainActivityViewModel) {
    val navController = rememberNavController()
    val isInitLoading by vm.isLoadingInit.observeAsState()
    NavHost(navController = navController, startDestination = "splash-screen") {
        composable(route = "splash-screen") { SplashScreen(navController = navController, isInitLoading) }
        composable(route = "main-screen") { PokeBrowser(navController = navController) }
        composable(route = "view-screen") { PokeViewer(navController = navController) }
    }
}