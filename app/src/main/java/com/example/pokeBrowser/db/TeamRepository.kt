package com.example.pokeBrowser.db

interface TeamRepository {
    suspend fun getTeams(): List<Team>
    suspend fun getTeamMembers(): List<TeamMember>

    suspend fun addTeamMember(member: TeamMember)
}
