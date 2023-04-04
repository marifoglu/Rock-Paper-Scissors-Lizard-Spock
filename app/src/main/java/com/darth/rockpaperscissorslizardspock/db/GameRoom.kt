package com.darth.rockpaperscissorslizardspock.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GameEntity::class], version = 1, exportSchema = false)
abstract class GameRoom : RoomDatabase() {

    abstract fun gameDao() : GameDAO

    companion object{
        @Volatile
        private var INSTANCE : GameRoom? = null

        fun getDB(context: Context) : GameRoom{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameRoom::class.java,
                    "game_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}