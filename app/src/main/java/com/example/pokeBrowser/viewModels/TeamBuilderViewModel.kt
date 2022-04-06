package com.example.pokeBrowser.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeBrowser.PokemonTeam
import com.example.pokeBrowser.PokemonTeamMember
import com.example.pokeBrowser.db.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TeamBuilderViewModel(val teamRepo: TeamRepository) : ViewModel() {

    private val _teams: MutableLiveData<List<PokemonTeam>> = MutableLiveData(ArrayList())
    val teams: LiveData<List<PokemonTeam>> = _teams

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadTeamsAndMembers() {
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val teamRequest = async { teamRepo.getTeams() }
            val teamMemberRequest = async { teamRepo.getTeamMembers() }

            val teams = teamRequest.await()
            val teamMembers = teamMemberRequest.await()

            if(teams.isEmpty()){
                _isLoading.postValue(false)
            } else {
                val mappedResult = mapToDisplayModels(teams, teamMembers)
                _teams.postValue(mappedResult)
                _isLoading.postValue(false)
            }
        }
    }

    private fun mapToDisplayModels(teams: List<Team>, teamMembers: List<TeamMember>): List<PokemonTeam> {
        val domainGroupedTeamMembers = teamMembers.groupBy(TeamMember::id) { m ->
            PokemonTeamMember(m.name)
        }

        val displayData = teams.map { t ->
            val teamId = t.id
            teamId?.let {
                val domainTeamMembers = domainGroupedTeamMembers[teamId]
                PokemonTeam(t.name, teamId, domainTeamMembers ?: emptyList())
            }
        }

        return displayData.filterNotNull()
    }
}
