package com.example.pokebrowser.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun NavigationBar(navController: NavHostController, isInitLoading: Boolean?, modifier: Modifier = Modifier) {
    if(isInitLoading == false) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            val textModifier = Modifier.padding(start = 8.dp)
            Row(modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clickable { navController.navigate("explore-screen") }
            ) {
                Icon(Icons.Rounded.Home, contentDescription = "Home", tint = Color.Black)
                Text(text = "Explore", modifier = textModifier)
            }
            Row(modifier = Modifier
                .weight(1f)
            ) {
                Icon(Icons.Rounded.Search, contentDescription = "Search", tint = Color.Black)
                Text(text = "Search", modifier = textModifier)
            }
        }
    }
}