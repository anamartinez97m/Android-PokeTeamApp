package com.mimo.poketeamapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.mimo.poketeamapp.database.AppDatabase
import com.mimo.poketeamapp.database.entity.PokemonEntity
import com.mimo.poketeamapp.databinding.ActivityPokeTeamBinding
import com.mimo.poketeamapp.model.Pokemon

class PokeTeamActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokeTeamBinding
    private lateinit var pokemonsAdapter: PokemonsAdapter
    private val pokemons = mutableListOf<Pokemon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokeTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Room
            .databaseBuilder(applicationContext, AppDatabase::class.java, "pokemon-database")
            .allowMainThreadQueries()
            .build()

        val pokemonsDB: List<PokemonEntity> = db.pokemonDao().getFavorites()
        var pokemonsToShow: List<Pokemon> = ArrayList()
        pokemonsDB.iterator().forEach { p ->
            val pokemon = Pokemon(p.name, p.url, p.id, null, p.base_experience, p.image, null)
            pokemonsToShow = pokemonsToShow + pokemon
        }

        initList()
        showPokemons(pokemonsToShow)

        val toolbar: Toolbar = findViewById(R.id.my_toolbar)
        toolbar.setTitle(R.string.title_pokemon_team)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun initList() {
        pokemonsAdapter = PokemonsAdapter(pokemons)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = pokemonsAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showPokemons(pokemonsResponse: List<Pokemon>) {
        pokemons.clear()
        pokemons.addAll(pokemonsResponse)
        pokemonsAdapter.notifyDataSetChanged()
    }
}