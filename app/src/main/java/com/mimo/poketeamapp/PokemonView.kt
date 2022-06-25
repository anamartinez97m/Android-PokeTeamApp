package com.mimo.poketeamapp

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso

@SuppressLint("CustomViewStyleable")
class PokemonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var pokemonViewName: TextView
    private var pokemonViewBaseExperience: TextView
    private var pokemonViewImage: ImageView
    private var pokemonViewTypes: TextView
    private var pokemonViewTypesList: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.pokemon_view, this, true)
        pokemonViewName = findViewById(R.id.pokemon_name)
        pokemonViewBaseExperience = findViewById(R.id.pokemon_base_experience)
        pokemonViewImage = findViewById(R.id.pokemon_image)
        pokemonViewTypes = findViewById(R.id.pokemon_types)
        pokemonViewTypesList = findViewById(R.id.pokemon_types_list)

        attrs?.let {
            val typed = context.obtainStyledAttributes(it, R.styleable.PokemonViewAttrs, 0, 0)
            val name = typed.getString(R.styleable.PokemonViewAttrs_pokemonview_name)
            val baseExperience = typed.getString(R.styleable.PokemonViewAttrs_pokemonview_base_experience)
            val types = typed.getString(R.styleable.PokemonViewAttrs_pokemonview_types)
            val typesList = typed.getString(R.styleable.PokemonViewAttrs_pokemonview_types_list)

            pokemonViewName.text = name
            pokemonViewBaseExperience.text = baseExperience
            pokemonViewTypes.text = types
            pokemonViewTypesList.text = typesList
        }
    }

    fun setPokemonViewName(name: String) {
        pokemonViewName.text = name
    }

    fun setPokemonViewBaseExperience(baseExperience: String) {
        pokemonViewBaseExperience.text = baseExperience
    }

    fun setPokemonViewImage(image: String) {
        Picasso.get().load(image).into(pokemonViewImage)
    }

    fun setPokemonViewTypes(types: String) {
        pokemonViewTypes.text = types
    }

    fun setPokemonViewTypesList(types: String) {
        pokemonViewTypesList.text = types
    }
}