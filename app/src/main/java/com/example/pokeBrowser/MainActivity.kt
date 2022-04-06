package com.example.pokeBrowser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pokeBrowser.composable.*
import com.example.pokeBrowser.ui.theme.PokeBrowserTheme
import com.example.pokeBrowser.viewModels.*
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.compose.getViewModel
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : ComponentActivity() {
    private val vm: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        startKoin {
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
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun Navigator(vm: MainActivityViewModel, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val navStackEntry by navController.currentBackStackEntryAsState()
    val currRoute = navStackEntry?.destination?.route ?: ""
    val currRouteArgs = navStackEntry?.arguments ?: Bundle()

    when {
        currRoute == "explore-screen" -> vm.updateTopBarTitle("Explore")
        currRoute == "search-screen" -> vm.updateTopBarTitle("Search")
        currRoute == "team-builder" -> vm.updateTopBarTitle("Your Teams")
        currRoute.contains("view-screen") -> {
            val newPokeTitle = currRouteArgs["pokeName"]?.toString()?.replaceFirstChar { c -> c.uppercase() } ?: ""
            vm.updateTopBarTitle(newPokeTitle)
        }
        else -> ""
    }

    val isInitLoading by vm.isLoadingInit.observeAsState()
    val pokeViewerVm = getViewModel<PokeViewerViewModel>()

    val scaffoldState = rememberScaffoldState()
    val crScope = rememberCoroutineScope()

    Column(verticalArrangement = Arrangement.Top, modifier = modifier) {
        val title by vm.topBarTitle.observeAsState()

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = { Text(title ?: "") },
                    navigationIcon = {
                        IconButton(onClick = {
                            crScope.launch { scaffoldState.drawerState.open() }
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = null)
                        }
                    }
                )
            },
            drawerContent = {
                NavigationBar(
                    navController = navController,
                    isInitLoading = isInitLoading,
                    scaffoldState = scaffoldState,
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = 4.dp)
                        .wrapContentHeight()
                )
            }
        ) {
            val screenModifier = remember { Modifier.padding(horizontal = 4.dp) }

            NavHost(navController = navController, startDestination = "splash-screen") {
                composable(route = "splash-screen") {
                    SplashScreen(
                        navController = navController,
                        isLoadingInit = isInitLoading
                    )
                }
                composable(route = "explore-screen") {
                    val pokeBrowserVm = viewModel<PokeBrowserViewModel>()
                    PokeBrowser(model = pokeBrowserVm, nav = navController, modifier = screenModifier)
                }
                composable(route = "view-screen/{pokeName}") { backStackEntry ->
                    pokeViewerVm.setPokemonName(backStackEntry.arguments?.getString("pokeName") ?: "ditto")
                    PokeViewer(model = pokeViewerVm, modifier = screenModifier)
                }
                composable(route = "search-screen") {
                    val pokeSearchVm = viewModel<PokeSearchViewModel>()
                    pokeViewerVm.setPokemonName("")
                    pokeViewerVm.setLoadingState(true)
                    PokeSearch(model = pokeSearchVm, viewerVm = pokeViewerVm, modifier = screenModifier)
                }
                composable(route = "team-builder") {
                    val teamBuilderVm = getViewModel<TeamBuilderViewModel>()
                    teamBuilderVm.loadTeamsAndMembers()
                    TeamBuilder(teamBuilderVm)
                }
            }
        }

    }
}
