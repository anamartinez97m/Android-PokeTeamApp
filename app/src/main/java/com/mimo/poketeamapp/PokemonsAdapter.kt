package com.mimo.poketeamapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mimo.poketeamapp.databinding.PokemonItemBinding
import com.mimo.poketeamapp.model.Pokemon
import com.squareup.picasso.Picasso

class PokemonsAdapter(private val pokemons: List<Pokemon>): RecyclerView.Adapter<PokemonsAdapter.PokemonViewHolder>() {

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = pokemons[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PokemonViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item, parent, false)
    )

    override fun getItemCount() = pokemons.size

    class PokemonViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = PokemonItemBinding.bind(view)

        fun bind(pokemon: Pokemon) {
            binding.pokemonName.text = pokemon.name
            binding.pokemonAbilityPoints.text = pokemon.abilityPoints.toString()
            Picasso.get().load(pokemon.image).into(binding.pokemonImage)
        }
    }
}