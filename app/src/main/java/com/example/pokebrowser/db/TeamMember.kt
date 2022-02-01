package com.example.pokebrowser.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TeamMember(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "pokemon_name") val name: String,
    @ColumnInfo(name = "team_name") val teamName: String
)
