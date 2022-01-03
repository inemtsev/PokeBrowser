package com.example.pokebrowser.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.pokebrowser.viewModels.PokeSearchViewModel
import com.example.pokebrowser.viewModels.PokeViewerViewModel

@Composable
fun PokeSearch(model: PokeSearchViewModel, viewerVm: PokeViewerViewModel) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var searchInput = remember { mutableStateOf(TextFieldValue()) }

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            TextWithSearch(searchInput, model, viewerVm)
        }

        PokeViewer(model = viewerVm)
    }
}

@Composable
fun TextWithSearch(
    textFieldValue: MutableState<TextFieldValue>,
    searchVm: PokeSearchViewModel,
    viewerVm: PokeViewerViewModel
) {
    TextField(
        value = textFieldValue.value,
        maxLines = 1,
        onValueChange = { textFieldValue.value = it },
        modifier = Modifier.height(60.dp)
    )
    Button(
        onClick = { searchVm.onSearchClick(viewerVm, textFieldValue.value.text) },
        modifier = Modifier
            .height(60.dp)
            .padding(1.dp)
    ) {
        Text(text = "Search")
    }
}