package com.example.pokeBrowser.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.pokeBrowser.viewModels.TeamBuilderViewModel

@Composable
fun TeamBuilder(model: TeamBuilderViewModel, modifier: Modifier = Modifier) {
    val loadingData = model.isLoading.observeAsState()
    val teamsData = model.teams.observeAsState()

    val loadingDataState = loadingData.value

    var isOpenDialog by remember { mutableStateOf(false) }
    var teamName by remember { mutableStateOf("") }

    fun openAddTeamScreen() {
        // open add team popover
    }

    if (loadingDataState == false) {
        Column(modifier = modifier) {
            val teamsDataState = teamsData.value
            teamsDataState?.let {
                CollapsableTeam(teams = teamsDataState)
            }
        }
    } else {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }

    FloatingActionButton(onClick = { isOpenDialog = true }) {
        Icon(Icons.Filled.Add, "")
    }
    if (isOpenDialog) {
        AlertDialog(
            onDismissRequest = { isOpenDialog = false },
            confirmButton = {
                TextButton(onClick = { /*TODO*/ }) { Text(text = "Add") }
            },
            dismissButton = {
                TextButton(onClick = {
                    isOpenDialog = false
                    teamName = ""
                }) { Text(text = "Cancel") }
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = "Please choose a team name...")
                    Box {
                        TextField(
                            value = teamName,
                            onValueChange = { teamName = it }
                        )
                    }
                }

            }
        )
    }
}
