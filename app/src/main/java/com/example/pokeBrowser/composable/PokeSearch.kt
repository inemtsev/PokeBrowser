package com.example.pokeBrowser.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.pokeBrowser.viewModels.PokeSearchViewModel
import com.example.pokeBrowser.viewModels.PokeViewerViewModel

@Composable
fun PokeSearch(model: PokeSearchViewModel, viewerVm: PokeViewerViewModel, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        val searchInput = remember { mutableStateOf(TextFieldValue()) }

        TextWithSearch(searchInput, model, viewerVm, modifier = Modifier.fillMaxWidth())
        PokeViewer(model = viewerVm)
    }
}

@Composable
fun TextWithSearch(
    textFieldValue: MutableState<TextFieldValue>,
    searchVm: PokeSearchViewModel,
    viewerVm: PokeViewerViewModel,
    modifier: Modifier = Modifier
) {
        Row(horizontalArrangement = Arrangement.Center, modifier = modifier) {
            TextField(
                value = textFieldValue.value,
                maxLines = 1,
                onValueChange = { textFieldValue.value = it },
                modifier = Modifier
                    .height(60.dp)
                    .weight(0.75f)
                    .padding(4.dp)
            )
            Button(
                onClick = { searchVm.onSearchClick(viewerVm, textFieldValue.value.text) },
                modifier = Modifier
                    .height(60.dp)
                    .weight(0.25f)
                    .padding(4.dp)
            ) {
                Text(text = "Add")
            }
        }
}
