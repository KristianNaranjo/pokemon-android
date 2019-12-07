package com.naranjo.kristian.pokemonandroid.ui.details

import android.os.Bundle
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.recyclerview.dataChanges
import com.naranjo.kristian.pokemonandroid.R
import com.naranjo.kristian.pokemonandroid.datastore.PokemonDataStore
import com.naranjo.kristian.pokemonandroid.service.Pokemon
import com.naranjo.kristian.pokemonandroid.service.PokemonJsonManager
import com.naranjo.kristian.pokemonandroid.ui.base.BaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class PokemonDetailsActivity : BaseActivity() {
    companion object {
        const val EXTRA_POKEMON = "extraPokemon"
    }

    private val pokemonDataStore: PokemonDataStore by inject()

    private lateinit var pokemonDetails: RecyclerView

    override val layoutResId: Int = R.layout.activity_pokemon_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pokemon = intent.getParcelableExtra(EXTRA_POKEMON) as Pokemon
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(pokemonDetails)
        val pokemonDetailsAdapter = PokemonDetailsAdapter()
        pokemonDetails.adapter = pokemonDetailsAdapter

        val pokemonList = mutableListOf<Pokemon>()
        disposables += pokemonDataStore.pokemon
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {
                    pokemonDetailsAdapter.submitList(it)
                    pokemonList.addAll(it)
                    pokemonDetailsAdapter.dataChanges()
                }
                .subscribe {
                    val position = pokemonList.indexOf(pokemon)
                    if (position != -1) {
                        pokemonDetails.scrollToPosition(pokemonList.indexOf(pokemon))
                    }
                }
    }

    override fun bindViews() {
        pokemonDetails = findViewById(R.id.details_list)
    }
}