package com.darth.rockpaperscissorslizardspock.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.darth.rockpaperscissorslizardspock.db.GameDAO
import com.darth.rockpaperscissorslizardspock.db.GameEntity
import com.darth.rockpaperscissorslizardspock.db.GameRepository
import com.darth.rockpaperscissorslizardspock.db.GameRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {

    val getGame : LiveData<List<GameEntity>>
    private var repository : GameRepository

    init {
        val gameDao = GameRoom.getDB(application).gameDao()
        repository = GameRepository(gameDao)
        getGame = repository.getAllData
    }

    fun addGame(gameEntity: GameEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addGame(gameEntity)
        }
    }
}