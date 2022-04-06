package com.example.pokeBrowser.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Team(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "team_name") val name: String,
)
