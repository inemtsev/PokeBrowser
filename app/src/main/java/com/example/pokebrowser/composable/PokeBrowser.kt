package com.example.pokebrowser.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.pokebrowser.viewModels.PokeBrowserViewModel

@Composable
fun PokeBrowser(model: PokeBrowserViewModel, nav: NavHostController, modifier: Modifier = Modifier) {
    val pokemonDataState by model.pokemonDataList.observeAsState()

    pokemonDataState?.let {
        val staticState = pokemonDataState ?: listOf()

        LazyColumn(modifier = modifier) {
            itemsIndexed(staticState) { i, p ->
                if (i == staticState.lastIndex) {
                    model.loadPokemonData()
                }

                val pokeImagePainter = rememberImagePainter(
                    data = p.imageUrl
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().clickable {
                        nav.navigate("view-screen/${p.name}")
                    }
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .wrapContentWidth()
                            .height(100.dp)
                    ) {
                        Image(
                            painter = pokeImagePainter,
                            contentDescription = p.name
                        )
                        Text(
                            text = p.name.replaceFirstChar { c -> c.uppercase() },
                            fontSize = 18.sp,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }

                    if (!p.types.isNullOrEmpty()) {
                        val context = LocalContext.current
                        Row(
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .wrapContentWidth()
                                .height(100.dp)
                                .padding(end = 8.dp)
                        ) {
                            p.types.forEach { typeFile ->
                                val drawableId = context.resources.getIdentifier(typeFile,"drawable", context.packageName)
                                val pokeTypePainter = rememberImagePainter(drawableId)
                                Image(
                                    painter = pokeTypePainter,
                                    contentDescription = p.name,
                                    modifier = Modifier
                                        .width(25.dp)
                                        .height(25.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}