package com.mimo.poketeamapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.navigation.NavigationView
import com.mimo.poketeamapp.databinding.ActivityMainBinding
import com.mimo.poketeamapp.model.Pokemon
import com.mimo.poketeamapp.network.GsonRequest
import com.mimo.poketeamapp.network.RequestManager
import com.mimo.poketeamapp.settings.SettingsActivity

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private var count = 1126
    private lateinit var pokemonView: PokemonView
    private lateinit var textViewError404: TextView
    private lateinit var loading: ProgressBar

    private lateinit var username: String
    private lateinit var password: String
    private lateinit var imageUri: String
    private lateinit var language: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.myToolbar
        toolbar.title = getString(R.string.app_name)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView: NavigationView = binding.navigationView
        navigationView.setNavigationItemSelectedListener(this)

        val swipeRefresh: SwipeRefreshLayout = binding.swipeRefresh
        val textViewSwipeRefresh: TextView = binding.textviewSwipeRefresh

        pokemonView = binding.pokemonView
        textViewError404 = binding.textviewError404
        textViewError404.visibility = View.GONE
        loading = binding.loading
        loading.visibility = View.VISIBLE

        val randomCount = rand(count)
        textViewSwipeRefresh.text = getString(R.string.swipe_refresh_textview)
        doRequest(randomCount)
        swipeRefresh.setOnRefreshListener {
            loading.visibility = View.VISIBLE
            textViewError404.visibility = View.GONE
            val randomCount2 = rand(count)
            textViewSwipeRefresh.text = getString(R.string.swipe_refresh_textview)
            doRequest(randomCount2)
            swipeRefresh.isRefreshing = false
        }

        val intent: Intent = intent
        username = intent.getStringExtra("username").toString()
        password = intent.getStringExtra("password").toString()
        imageUri = intent.getStringExtra("imageUri").toString()
        language = intent.getStringExtra("language").toString()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)
        return true
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = binding.drawerLayout
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_settings -> {
            val intent = Intent(this, SettingsActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("password", password)
            intent.putExtra("imageUri", imageUri)
            intent.putExtra("language", language)
            startActivity(intent)
            true
        }
        R.id.menu_sign_out -> {
            LogoutConfirmationDialogFragment().show(supportFragmentManager, LogoutConfirmationDialogFragment.TAG)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.menu_nav_team -> {
            val intent = Intent(this, PokeTeamActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.menu_nav_poke_list -> {
            val intent = Intent(this, PokeListActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.menu_nav_tutorial -> {
            val intent = Intent(this, TutorialActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.menu_nav_about -> {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
            true
        }
        else -> {
            true
        }
    }

    @SuppressLint("StringFormatMatches")
    private fun doRequest(id: Int) {
        val url = "https://pokeapi.co/api/v2/pokemon/$id"
        val gsonRequest = GsonRequest(url,
            Pokemon::class.java, null,
            { response ->
                loading.visibility = View.GONE
                pokemonView.visibility = View.VISIBLE
                textViewError404.visibility = View.GONE
                pokemonView.setPokemonViewName(getString(R.string.name) +response.name)
                pokemonView.setPokemonViewBaseExperience(getString(R.string.base_experience) + response.base_experience.toString())
                pokemonView.setPokemonViewTypes(getString(R.string.pokemonview_types))
                pokemonView.setPokemonViewImage(response.sprites?.other?.home?.front_default.toString())
                var strTypesList = ""
                response.types?.forEachIndexed { index, element ->
                    strTypesList = strTypesList.plus(element.type.name)
                    if(index != response.types.lastIndex) {
                        strTypesList = strTypesList.plus(", ")
                    }
                }
                pokemonView.setPokemonViewTypesList(strTypesList)
            },
            {
                loading.visibility = View.GONE
                pokemonView.visibility = View.GONE
                textViewError404.visibility = View.VISIBLE
                textViewError404.text = getString(R.string.api_pokemon_not_found)
            }
        )
        RequestManager.getInstance(this).addToRequestQueue(gsonRequest)
    }

    private fun rand(end: Int): Int {
        val start = 1
        require(start <= end) { "Illegal Argument" }
        return (Math.random() * (end - start + 1)).toInt() + start
    }
}