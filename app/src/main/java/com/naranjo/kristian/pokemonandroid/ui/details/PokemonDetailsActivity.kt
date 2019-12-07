package com.naranjo.kristian.pokemonandroid.ui.details

import android.os.Bundle
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.recyclerview.dataChanges
import com.naranjo.kristian.pokemonandroid.R
import com.naranjo.kristian.pokemonandroid.service.Pokemon
import com.naranjo.kristian.pokemonandroid.service.PokemonJsonManager
import com.naranjo.kristian.pokemonandroid.ui.base.BaseActivity
import io.reactivex.rxkotlin.plusAssign
import org.koin.android.ext.android.inject

class PokemonDetailsActivity : BaseActivity() {
    companion object {
        const val EXTRA_POKEMON = "extraPokemon"
    }

    private val datastore: PokemonJsonManager by inject()

    private lateinit var pokemonDetails: RecyclerView

    override val layoutResId: Int = R.layout.activity_pokemon_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pokemon = intent.getParcelableExtra(EXTRA_POKEMON) as Pokemon
        val pokemonList = datastore.pokemon
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(pokemonDetails)
        val pokemonDetailsAdapter = PokemonDetailsAdapter()
        pokemonDetails.adapter = pokemonDetailsAdapter
        pokemonDetailsAdapter.submitList(pokemonList)
        disposables += pokemonDetailsAdapter.dataChanges()
                .subscribe { pokemonDetails.scrollToPosition(pokemonList.indexOf(pokemon)) }
    }

    override fun bindViews() {
        pokemonDetails = findViewById(R.id.details_list)
    }
}