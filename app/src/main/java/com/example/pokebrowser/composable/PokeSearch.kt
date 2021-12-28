package com.example.pokebrowser.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.pokebrowser.viewModels.PokeSearchViewModel

@Composable
fun PokeSearch(model: PokeSearchViewModel) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val searchInput = remember { mutableStateOf(TextFieldValue()) }

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            TextField(
                value = searchInput.value,
                onValueChange = { sv -> searchInput.value = sv },
                modifier = Modifier.height(60.dp)
            )
            Button(
                onClick = { model.onSearchClick(searchInput.value.text) },
                modifier = Modifier
                    .height(60.dp)
                    .padding(1.dp)
            ) {
                Text(text = "Button")
            }
        }

        val pokeViewerState = model.pokeViewerVm.observeAsState()
        if (pokeViewerState.value != null) {
            PokeViewer(model.pokeViewerVm.value)
        }
    }
}