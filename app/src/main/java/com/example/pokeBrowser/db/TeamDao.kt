package com.example.pokeBrowser.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TeamDao {
    @Query("SELECT * FROM Team")
    suspend fun getAllTeams(): List<Team>

    @Insert
    suspend fun insertTeam(vararg member: Team)

    @Delete
    suspend fun deleteTeam(member: Team)
}
