package com.naranjo.kristian.pokemonandroid.ui.pokedex

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.naranjo.kristian.pokemonandroid.R
import com.naranjo.kristian.pokemonandroid.datastore.Pokemon
import com.naranjo.kristian.pokemonandroid.ui.base.BaseActivity
import com.naranjo.kristian.pokemonandroid.ui.widgets.MarginItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokedexActivity : BaseActivity() {

    override val layoutResId: Int = R.layout.activity_main

    private val viewModel: PokedexViewModel by viewModel()

    private lateinit var pokemonList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pokemonListAdapter = PokedexAdapter(::onPokedexEntryClicked)
        pokemonList.adapter = pokemonListAdapter

        viewModel.apply {
            pokemonListData.observe(
                this@PokedexActivity,
                Observer { pokemonListAdapter.submitList(it) }
            )
        }
    }

    override fun bindViews() {
        pokemonList = findViewById<RecyclerView>(R.id.pokemon_list).apply {
            layoutManager = LinearLayoutManager(this@PokedexActivity, RecyclerView.VERTICAL, false)
            addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.pokedex_items_spacing).toInt()))
        }
    }

    private fun onPokedexEntryClicked(pokemon: Pokemon) {

    }
}