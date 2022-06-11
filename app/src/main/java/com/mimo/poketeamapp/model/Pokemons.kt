package com.mimo.poketeamapp.model

import androidx.room.*
import com.google.gson.annotations.SerializedName
import java.sql.Types

data class Pokemons(
    val count: Int?,
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
    val url: String?,
    val id: String,
    val sprites: Sprites?,
    val base_experience: Int,
    val image: String?,
    val types: Array<PokemonTypes>?) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Pokemon

        if (name != other.name) return false
        if (url != other.url) return false
        if (id != other.id) return false
        if (sprites != other.sprites) return false
        if (base_experience != other.base_experience) return false
        if (image != other.image) return false
        if (types != null) {
            if (other.types == null) return false
            if (!types.contentEquals(other.types)) return false
        } else if (other.types != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + (sprites?.hashCode() ?: 0)
        result = 31 * result + base_experience
        result = 31 * result + (image?.hashCode() ?: 0)
        result = 31 * result + (types?.contentHashCode() ?: 0)
        return result
    }
}

data class Sprites(
    val other: OtherSprites
)

data class OtherSprites(
    val home: HomeOtherSprite
)

data class HomeOtherSprite(
    val front_default: String
)

data class PokemonTypes(
    val type: PokemonType
)

data class PokemonType(
    val name: String
)