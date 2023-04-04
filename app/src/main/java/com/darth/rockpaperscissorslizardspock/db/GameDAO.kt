package com.darth.rockpaperscissorslizardspock.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GameDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGame(gameEntity: GameEntity)

    @Query("SELECT * FROM game_table ORDER BY id DESC")
    fun getGame() : LiveData<List<GameEntity>>
}