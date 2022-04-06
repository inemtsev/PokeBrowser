package com.example.pokeBrowser.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TeamMemberDao {
    @Query("SELECT * FROM TeamMember")
    suspend fun getAllTeamMembers(): List<TeamMember>

    @Insert
    suspend fun insertMembers(vararg member: TeamMember)

    @Delete
    suspend fun deleteMember(member: TeamMember)
}