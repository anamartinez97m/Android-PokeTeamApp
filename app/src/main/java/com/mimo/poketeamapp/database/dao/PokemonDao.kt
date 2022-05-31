package com.mimo.poketeamapp.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.mimo.poketeamapp.database.entity.PokemonEntity

@Dao
interface PokemonDao {
    @Query("SELECT * FROM PokemonEntity WHERE favorite = 1")
    fun getFavorites(): List<PokemonEntity>

    @Query("INSERT INTO PokemonEntity(name, url, api_pokemon_id, sprites, base_experience, favorite) " +
            " VALUES (:name, :url, :api_pokemon_id, :sprites, :base_experience, 1) ")
    fun addFavorite(name: String, url: String, api_pokemon_id: String, sprites: String, base_experience: Int)
}
