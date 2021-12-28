package com.example.pokebrowser.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.pokebrowser.Pokemon
import com.example.pokebrowser.PokemonStat
import com.example.pokebrowser.viewModels.PokeViewerViewModel

@Composable
fun PokeViewer(model: PokeViewerViewModel?, modifier: Modifier = Modifier) {
    if(model != null) {
        val modelState = model.pokemonData.observeAsState()

        if (modelState.value != null) {
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
                    Spacer(modifier = Modifier.height(8.dp))
                }
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