package com.example.pokeBrowser.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.pokeBrowser.Pokemon
import com.example.pokeBrowser.PokemonStat
import com.example.pokeBrowser.viewModels.PokeViewerViewModel
import com.example.pokeBrowser.viewModels.TeamMemberAddViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun PokeViewer(model: PokeViewerViewModel, modifier: Modifier = Modifier) {
    val modelState = model.pokemonData.observeAsState()
    val loadingState = model.isLoading.observeAsState()
    val teamMemberAddVm = getViewModel<TeamMemberAddViewModel>()

    LaunchedEffect(true) {
        model.getPokemonData()
    }

    if (loadingState.value != true && modelState.value != null) {
        val staticModelState = modelState.value

        staticModelState?.let {
            val cardModifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start,
                modifier = modifier
            ) {
                PokeBasicsCard(
                    pokemon = staticModelState,
                    modifier = cardModifier
                )
                PokeStatsCard(
                    stats = staticModelState.baseStats,
                    modifier = cardModifier
                )
                TeamMemberAdd(
                    modelMember = teamMemberAddVm,
                    pokemon = staticModelState
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun PokeBasicsCard(pokemon: Pokemon, modifier: Modifier = Modifier) {
    val valuePadding = PaddingValues(start = 4.dp)
    val wrapperPadding = PaddingValues(all = 12.dp)

    Card(
        elevation = 10.dp,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(150.dp)
                .padding(wrapperPadding)
        ) {
            val pokeImagePainter = rememberImagePainter(pokemon.imageUrl)

            Image(
                painter = pokeImagePainter,
                contentDescription = pokemon.name,
                modifier = Modifier.fillMaxHeight()
            )
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 4.dp)
            ) {
                Row {
                    Text(
                        text = "Title: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = pokemon.name.replaceFirstChar { c -> c.uppercase() },
                        modifier = Modifier.padding(valuePadding)
                    )
                }
                Row {
                    Text(
                        text = "Abilities: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = pokemon.abilities.joinToString(", "),
                        modifier = Modifier.padding(valuePadding)
                    )
                }
                Row {
                    Text(
                        text = "Height: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = pokemon.height.toString(),
                        modifier = Modifier.padding(valuePadding)
                    )
                }
                Row {
                    Text(
                        text = "Weight: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = pokemon.weight.toString(),
                        modifier = Modifier.padding(valuePadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PokeStatsCard(stats: List<PokemonStat>, modifier: Modifier = Modifier) {
    val valuePadding = PaddingValues(start = 4.dp)
    val wrapperPadding = PaddingValues(all = 12.dp)

    Card(
        elevation = 10.dp,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(wrapperPadding)){
            Text(
                text = "Base Stats",
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            stats.forEach { s ->
                Row {
                    Text(
                        text = s.statName.replaceFirstChar { c -> c.uppercase() },
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(0.5f)
                    )
                    Text(
                        text = s.statNumber.toString(),
                        modifier = Modifier
                            .padding(valuePadding)
                            .weight(0.5f)
                    )
                }
            }
        }
    }
}