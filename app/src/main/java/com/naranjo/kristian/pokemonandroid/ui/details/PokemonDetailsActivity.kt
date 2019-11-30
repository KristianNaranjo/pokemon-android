package com.naranjo.kristian.pokemonandroid.ui.details

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.naranjo.kristian.pokemonandroid.R
import com.naranjo.kristian.pokemonandroid.datastore.Pokemon
import com.naranjo.kristian.pokemonandroid.ui.base.BaseActivity

class PokemonDetailsActivity : BaseActivity() {
    companion object {
        const val EXTRA_POKEMON = "extraPokemon"
    }

    lateinit var pokemonNumber: TextView
    lateinit var pokemonName: TextView
    lateinit var pokemonImage: ImageView
    lateinit var pokemonTypes: RecyclerView

    override val layoutResId: Int = R.layout.activity_pokemon_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pokemon = intent.getParcelableExtra(EXTRA_POKEMON) as Pokemon
        pokemonNumber.text = pokemon.number
        pokemonName.text = pokemon.name
        pokemonImage.load(pokemon.imageUrl)

        val pokemonTypesAdapter = PokemonTypesAdapter()
        pokemonTypes.adapter = pokemonTypesAdapter
        pokemonTypesAdapter.submitList(pokemon.types)
    }

    override fun bindViews() {
        pokemonNumber = findViewById(R.id.details_number)
        pokemonName = findViewById(R.id.details_name)
        pokemonImage = findViewById(R.id.details_image)
        pokemonTypes = findViewById<RecyclerView>(R.id.details_types).apply {
            layoutManager = LinearLayoutManager(this@PokemonDetailsActivity, RecyclerView.HORIZONTAL, false)
        }
    }
}