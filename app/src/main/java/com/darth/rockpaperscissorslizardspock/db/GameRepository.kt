package com.darth.rockpaperscissorslizardspock.db

import androidx.lifecycle.LiveData

class GameRepository(private val gameDao: GameDAO) {
    val getAllData : LiveData<List<GameEntity>> = gameDao.getGame()

    suspend fun addGame(gameInsert: GameEntity){
        gameDao.addGame(gameInsert)
    }
}