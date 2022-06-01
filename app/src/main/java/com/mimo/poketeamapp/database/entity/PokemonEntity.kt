package com.mimo.poketeamapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true) val db_id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "api_pokemon_id") val id: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "base_experience") val base_experience: Int,
    @ColumnInfo(name = "favorite") val favorite: Boolean
)