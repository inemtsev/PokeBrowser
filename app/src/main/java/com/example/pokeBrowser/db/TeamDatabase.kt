package com.example.pokeBrowser.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities=[Team::class], version = 1)
abstract class TeamDatabase : RoomDatabase() {
    abstract fun teamDao(): TeamDao
}
