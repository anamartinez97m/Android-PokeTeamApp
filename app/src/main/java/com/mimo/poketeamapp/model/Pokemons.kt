package com.mimo.poketeamapp.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

data class Pokemons(
    @SerializedName("results") val results: Array<Pokemon>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Pokemons

        if (!results.contentEquals(other.results)) return false

        return true
    }

    override fun hashCode(): Int {
        return results.contentHashCode()
    }
}

data class Pokemon(
    val name: String,
    val url: String,
    val id: String,
    val sprites: Sprites?,
    val base_experience: Int,
    val image: String?)

data class Sprites(
    val other: OtherSprites
)

data class OtherSprites(
    val home: HomeOtherSprite
)

data class HomeOtherSprite(
    val front_default: String
)