package com.example.pokeBrowser.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities=[TeamMember::class], version = 1)
abstract class TeamMemberDatabase : RoomDatabase() {
    abstract fun teamMemberDao(): TeamMemberDao
}