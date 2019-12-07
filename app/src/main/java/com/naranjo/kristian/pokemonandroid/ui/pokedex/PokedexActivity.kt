package com.naranjo.kristian.pokemonandroid.ui.pokedex

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.widget.queryTextChanges
import com.naranjo.kristian.pokemonandroid.R
import com.naranjo.kristian.pokemonandroid.service.Pokemon
import com.naranjo.kristian.pokemonandroid.ui.base.BaseActivity
import com.naranjo.kristian.pokemonandroid.ui.details.PokemonDetailsActivity
import com.naranjo.kristian.pokemonandroid.ui.widgets.LinearLayoutMarginItemDecoration
import io.reactivex.rxkotlin.plusAssign
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokedexActivity : BaseActivity() {

    override val layoutResId: Int = R.layout.activity_pokedex

    private val viewModel: PokedexViewModel by viewModel()

    private lateinit var pokedex: RecyclerView
    private lateinit var searchBar: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pokemonListAdapter = PokedexAdapter(::onPokedexEntryClicked)
        pokedex.adapter = pokemonListAdapter

        viewModel.apply {
            pokemonListData.observe(
                    this@PokedexActivity,
                    Observer {
                        pokemonListAdapter.submitList(it)
                    }
            )
        }
    }

    override fun onStart() {
        super.onStart()

        disposables += searchBar.queryTextChanges()
                .subscribe { viewModel.onSearchQueryEntered(it.toString()) }
    }

    override fun bindViews() {
        pokedex = findViewById<RecyclerView>(R.id.pokemon_list).apply {
            layoutManager = LinearLayoutManager(this@PokedexActivity, RecyclerView.VERTICAL, false)
            addItemDecoration(LinearLayoutMarginItemDecoration(resources.getDimension(R.dimen.pokedex_items_spacing).toInt(), LinearLayoutMarginItemDecoration.Orientation.VERTICAL))
        }
        searchBar = findViewById(R.id.search_bar)
    }

    private fun onPokedexEntryClicked(pokemon: Pokemon) {
        val intent = Intent(this, PokemonDetailsActivity::class.java)
        intent.putExtra(PokemonDetailsActivity.EXTRA_POKEMON, pokemon)
        startActivity(intent)
    }
}