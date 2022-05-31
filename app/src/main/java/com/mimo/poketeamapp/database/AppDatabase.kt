package com.mimo.poketeamapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mimo.poketeamapp.database.dao.PokemonDao
import com.mimo.poketeamapp.database.dao.UserDao
import com.mimo.poketeamapp.database.entity.PokemonEntity
import com.mimo.poketeamapp.database.entity.User

@Database(entities = [User::class, PokemonEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun pokemonDao(): PokemonDao
}