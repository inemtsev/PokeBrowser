package com.example.pokebrowser.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokebrowser.Pokemon
import coil.compose.rememberImagePainter

@Composable
fun PokeBrowserItem(model: Pokemon, modifier: Modifier = Modifier) {
    val types = model.getTypeBitmaps()
    val mainImagePainter = rememberImagePainter(model.image)

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
        .fillMaxWidth()
        .height(100.dp)) {

        Image(painter = mainImagePainter, contentDescription = model.name)
        Text(text = model.name)
    }
}