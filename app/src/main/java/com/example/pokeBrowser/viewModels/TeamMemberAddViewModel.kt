package com.example.pokeBrowser.viewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeBrowser.Pokemon
import com.example.pokeBrowser.db.Team
import com.example.pokeBrowser.db.TeamMember
import com.example.pokeBrowser.db.TeamRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TeamMemberAddViewModel(val teamRepository: TeamRepository) : ViewModel() {
    private val _teams: MutableLiveData<List<Team>> = MutableLiveData(emptyList())
    val teams: LiveData<List<Team>> = _teams

    fun loadTeams() {
        viewModelScope.launch(Dispatchers.IO) {
            val loadTeamsRequest = async { teamRepository.getTeams() }
            val loadTeamsResponse = loadTeamsRequest.await()

            _teams.postValue(loadTeamsResponse)
        }
    }

    fun addToTeam(context: Context, pokemon: Pokemon, selectedTeam: Int) {
        val team = _teams.value?.first { t -> t.id == selectedTeam }
        team?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val addMemberRequest = async {
                    teamRepository.addTeamMember(
                        TeamMember(name = pokemon.name, teamId = team.id ?: 0)
                    )
                }
                addMemberRequest.await()
                Toast.makeText(
                    context,
                    "Added ${pokemon.name} to ${team.name}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
