package com.example.pokeBrowser.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.pokeBrowser.Pokemon
import com.example.pokeBrowser.viewModels.TeamMemberAddViewModel

@Composable
fun TeamMemberAdd(modelMember: TeamMemberAddViewModel, pokemon: Pokemon, modifier: Modifier = Modifier) {
    var selectedTeam by remember { mutableStateOf(0) }
    var expandedTeamSelect by remember { mutableStateOf(false) }
    
    val localContext = LocalContext.current
    val availableTeams = modelMember.teams.observeAsState()

    modelMember.loadTeams()

    Row(modifier) {
        Button(
            onClick = { expandedTeamSelect = true },
            modifier = Modifier
                .height(60.dp)
                .weight(0.75f)
                .padding(4.dp)
        ) {
            "Select Team"
        }
        DropdownMenu(
            expanded = expandedTeamSelect,
            onDismissRequest = { expandedTeamSelect = false }
        ) {
            availableTeams.value?.let {
                it.forEach {
                    DropdownMenuItem(
                        onClick = {
                            selectedTeam = it.id ?: 0
                            expandedTeamSelect = false
                        }
                    ) {
                        Text(text = it.name)
                    }
                }
            }
        }

        Button(onClick = {
            modelMember.addToTeam(
                context = localContext,
                pokemon = pokemon,
                selectedTeam = selectedTeam
            )
        }) {
            "Add"
        }
    }
}