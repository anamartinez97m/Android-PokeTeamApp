package com.mimo.poketeamapp

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView

class PokemonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    lateinit var pokemonViewName: String

    init {
        LayoutInflater.from(context).inflate(R.layout.pokemon_view, this, true)
        val pokemonViewName: TextView = findViewById(R.id.pokemon_name)

        attrs?.let {
            val typed = context.obtainStyledAttributes(it, R.styleable.PokemonViewAttrs, 0, 0)
            val name = typed.getString(R.styleable.PokemonViewAttrs_pokemonview_name)

            pokemonViewName.text = name
        }
    }
}