package com.darth.rockpaperscissorslizardspock.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("game_table")
data class GameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val winner: String,
    val loser: String
)