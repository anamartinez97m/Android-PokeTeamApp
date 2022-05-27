package com.mimo.poketeamapp.model

import com.google.gson.annotations.SerializedName

data class Pokemons(@SerializedName("pokemons") val pokemons: Array<Pokemon>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Pokemons

        if (!pokemons.contentEquals(other.pokemons)) return false

        return true
    }

    override fun hashCode(): Int {
        return pokemons.contentHashCode()
    }
}

data class Pokemon(val name: String, val image: String, val abilityPoints: Int)