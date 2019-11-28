package com.naranjo.kristian.pokemonandroid.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.naranjo.kristian.pokemonandroid.R
import com.naranjo.kristian.pokemonandroid.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonListActivity : BaseActivity() {

    override val layoutResId: Int = R.layout.activity_main

    private val viewModel: PokemonListViewModel by viewModel()

    private lateinit var pokemonList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pokemonListAdapter = PokemonListAdapter()
        pokemonList.adapter = pokemonListAdapter

        viewModel.apply {
            pokemonListData.observe(
                this@PokemonListActivity,
                Observer { pokemonListAdapter.submitList(it) }
            )
        }
    }

    override fun bindViews() {
        pokemonList = findViewById(R.id.pokemon_list)
        pokemonList.layoutManager = LinearLayoutManager(this@PokemonListActivity, RecyclerView.VERTICAL, false)
    }
}