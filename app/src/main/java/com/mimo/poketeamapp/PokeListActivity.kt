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
        setContentView(R.layout.activity_poke_list)

        binding = ActivityPokeListBinding.inflate(layoutInflater)
        initList()

        val toolbar: Toolbar = findViewById(R.id.my_toolbar)
        toolbar.setTitle(R.string.title_poke_list)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //doRequestSinglePokemon()
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

//    private fun doRequestSinglePokemon() {
//        val url = "https://pokeapi.co/api/v2/pokemon/eevee"
//        val gsonRequest = GsonRequest(url,
//            Pokemon::class.java, null,
//            { response ->
//                Log.d("response SinglePokemon", response.toString())
//                showPokemon(response)
//            },
//            {
//                Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
//            }
//        )
//        RequestManager.getInstance(this).addToRequestQueue(gsonRequest)
//    }

    private fun doRequest() {
        val pokemonsArray: ArrayList<Pokemon> = ArrayList()
        val url = "https://pokeapi.co/api/v2/pokemon"
        val gsonRequest = GsonRequest(url,
            Pokemons::class.java, null,
            { response ->
                Log.d("response", response.toString())
                for(p: Pokemon in response.results) {
                    pokemonsArray.add(doRequestSearchPokemon(p.url))
                }
                showPokemons(pokemonsArray)
            },
            {
                Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
            }
        )
        RequestManager.getInstance(this).addToRequestQueue(gsonRequest)
    }

    private fun doRequestSearchPokemon(searchUrl: String): Pokemon {
        var pokemonToReturn: Pokemon = Pokemon("","","",null,0)
        val gsonRequest = GsonRequest(
            searchUrl,
            Pokemon::class.java, null,
            { response ->
                pokemonToReturn = response
            },
            {
                Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
            }
        )
        RequestManager.getInstance(this).addToRequestQueue(gsonRequest)
        return pokemonToReturn
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showPokemons(pokemonsResponse: List<Pokemon>) {
        pokemons.clear()
        pokemons.addAll(pokemonsResponse)
        pokemonsAdapter.notifyDataSetChanged()
    }

//    @SuppressLint("NotifyDataSetChanged")
//    private fun showPokemon(pokemonResponse: Pokemon) {
//        Toast.makeText(this, "Has desbloqueado a ${pokemonResponse.name}!", Toast.LENGTH_SHORT).show()
//    }
}