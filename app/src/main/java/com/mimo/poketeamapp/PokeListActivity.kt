package com.mimo.poketeamapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.mimo.poketeamapp.databinding.ActivityPokeListBinding
import com.mimo.poketeamapp.model.Pokemon
import com.mimo.poketeamapp.model.Pokemons
import com.mimo.poketeamapp.network.GsonRequest
import com.mimo.poketeamapp.network.RequestManager

class PokeListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokeListBinding
    private lateinit var pokemonsAdapter: PokemonsAdapter
    private val pokemons = mutableListOf<Pokemon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initList()

        val toolbar: Toolbar = findViewById(R.id.my_toolbar)
        toolbar.setTitle(R.string.title_poke_list)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        doRequest()
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

    private fun doRequest() {
        val url = "https://pokeapi.co/api/v2/pokemon"
        val gsonRequest = GsonRequest(url,
            Pokemons::class.java, null,
            { response ->
                response.results.iterator().forEach { p ->
                    p.url?.let { doRequestSearchPokemon(it) }
                }
            },
            {
                Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
            }
        )
        RequestManager.getInstance(this).addToRequestQueue(gsonRequest)
    }

    private fun doRequestSearchPokemon(searchUrl: String) {
        val gsonRequest = GsonRequest(
            searchUrl,
            Pokemon::class.java, null,
            { response ->
                concatPokemon(response)
            },
            {
                Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
            }
        )
        RequestManager.getInstance(this).addToRequestQueue(gsonRequest)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun concatPokemon(pokemonResponse: Pokemon) {
        pokemons.add(pokemonResponse)
        pokemonsAdapter.notifyDataSetChanged()
    }
}