package com.example.pokebrowser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.pokebrowser.ui.theme.PokeBrowserTheme

class MainActivity : ComponentActivity() {
    private val vm: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        vm.getPokemonList()
        super.onCreate(savedInstanceState)
        setContent {
            PokeBrowserTheme {
                Text(text = "Gotta catch'em all!", Modifier.padding(8.dp))
                Surface(color = MaterialTheme.colors.background) {
                    PokeBrowserContent()
                }
            }
        }
    }
}

@Composable
fun PokeBrowserContent() {
    LazyColumn() {

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PokeBrowserTheme {
        Greeting("Android")
    }
}