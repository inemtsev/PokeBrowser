package com.example.pokebrowser.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.Icon
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch

const val NAV_TEXT_SIZE = 28

@Composable
fun NavigationBar(
    navController: NavHostController,
    isInitLoading: Boolean?,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier
) {
    if(isInitLoading == false) {
        val crScope = rememberCoroutineScope()

        val context = LocalContext.current
        val navLogoDrawableId = remember { context.resources.getIdentifier("pikachu_go","drawable", context.packageName) }
        val navLogoPainter = rememberImagePainter(navLogoDrawableId)

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start,
            modifier = modifier
        ) {
            val rowModifier = Modifier
                .padding(top = 8.dp)
                .wrapContentHeight()
                .fillMaxWidth()

            val textModifier = Modifier
                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                .wrapContentHeight()

            val iconModifier = Modifier.padding(top = 16.dp)

            Box(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                Image(
                    painter = navLogoPainter,
                    contentDescription = "Pikachu logo",
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Row(modifier = rowModifier.clickable {
                navController.navigate("explore-screen")
                crScope.launch {
                    scaffoldState.drawerState.close()
                }
            }) {
                Icon(
                    Icons.Rounded.Home,
                    contentDescription = "Home",
                    tint = Color.Black,
                    modifier = iconModifier
                )
                Text(text = "Explore", modifier = textModifier, fontSize = NAV_TEXT_SIZE.sp)
            }
            Row(modifier = rowModifier.clickable {
                navController.navigate("search-screen")
                crScope.launch {
                    scaffoldState.drawerState.close()
                }
            }) {
                Icon(
                    Icons.Rounded.Search,
                    contentDescription = "Search",
                    tint = Color.Black,
                    modifier = iconModifier
                )
                Text(text = "Search", modifier = textModifier, fontSize = NAV_TEXT_SIZE.sp)
            }
        }
    }
}