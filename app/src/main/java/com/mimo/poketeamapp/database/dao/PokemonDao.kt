package com.mimo.poketeamapp.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.mimo.poketeamapp.database.entity.PokemonEntity
import com.mimo.poketeamapp.model.Sprites

@Dao
interface PokemonDao {
    @Query("SELECT * FROM PokemonEntity WHERE favorite = 1")
    fun getFavorites(): List<PokemonEntity>

    @Query("INSERT INTO PokemonEntity(name, url, api_pokemon_id, image, base_experience, favorite) " +
            " VALUES (:name, :url, :api_pokemon_id, :image, :base_experience, 1) ")
    fun addFavorite(name: String, url: String, api_pokemon_id: String, image: String, base_experience: Int)

    @Query("DELETE FROM PokemonEntity")
    fun removeAllFavorites()
}
