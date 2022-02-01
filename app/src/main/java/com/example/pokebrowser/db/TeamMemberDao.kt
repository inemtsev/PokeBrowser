package com.example.pokebrowser.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TeamMemberDao {
    @Query("SELECT * FROM TeamMember")
    fun getAllTeamMembers(): List<TeamMember>

    @Insert
    fun insertMembers(vararg member: TeamMember)

    @Delete
    fun deleteMember(member: TeamMember)
}