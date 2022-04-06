package com.example.pokeBrowser.db

import android.content.Context
import androidx.room.Room

class TeamRepositoryImpl(context: Context) : TeamRepository {
    private var teamDao: TeamDao
    private var teamMemberDao: TeamMemberDao

    init {
        val teamDatabase = Room.databaseBuilder(context, TeamDatabase::class.java, "team-db").build()
        teamDao = teamDatabase.teamDao()

        val teamMemberDatabase = Room.databaseBuilder(context, TeamMemberDatabase::class.java, "team-member-db").build()
        teamMemberDao = teamMemberDatabase.teamMemberDao()
    }

    override suspend fun getTeams(): List<Team> = teamDao.getAllTeams()

    override suspend fun getTeamMembers(): List<TeamMember> = teamMemberDao.getAllTeamMembers()

    override suspend fun addTeamMember(member: TeamMember) = teamMemberDao.insertMembers(member)
}
