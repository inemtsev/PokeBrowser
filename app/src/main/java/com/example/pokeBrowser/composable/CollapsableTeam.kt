package com.example.pokeBrowser.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pokeBrowser.PokemonTeam

@Composable
fun CollapsableTeam(
    teams: List<PokemonTeam>,
    modifier: Modifier = Modifier
) {
    val collapsedState = remember(teams) {
        teams.map { true }.toMutableStateList()
    }

    LazyColumn(modifier) {
        teams.forEachIndexed { i, team ->
            val collapsed = collapsedState[i]
            item(key = "team_$i") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        collapsedState[i] = !collapsed
                    }
                ) {
                    Icon(
                        Icons.Default.run {
                            if (collapsed)
                                KeyboardArrowDown
                            else
                                KeyboardArrowUp
                        },
                        contentDescription = "",
                        tint = Color.LightGray,
                    )
                    Text(
                        text = team.teamName,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .weight(1f)
                    )
                }
                Divider()
            }

            if(!collapsed) {
                items(items = team.teamMembers) { m ->
                    Row {
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(text = m.name, modifier = Modifier.padding(vertical = 8.dp))
                    }

                    Divider()
                }
            }
        }
    }
}