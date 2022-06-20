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

    @Query("UPDATE PokemonEntity SET favorite = 0 WHERE db_id > 0")
    fun removeAllFavorites()

    @Query("UPDATE PokemonEntity SET favorite = 0 WHERE api_pokemon_id = :api_pokemon_id")
    fun removeFavorite(api_pokemon_id: String)

    @Query("SELECT COUNT(*) FROM PokemonEntity WHERE favorite = 1")
    fun getFavoritesCount(): Int

    @Query("SELECT favorite FROM PokemonEntity WHERE api_pokemon_id = :api_pokemon_id")
    fun isPokemonFavorite(api_pokemon_id: String): Boolean
}
