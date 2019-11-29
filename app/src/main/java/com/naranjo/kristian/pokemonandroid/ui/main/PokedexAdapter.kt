package com.naranjo.kristian.pokemonandroid.ui.main

import android.graphics.drawable.GradientDrawable
import android.view.ViewGroup
import android.widget.TextView
import com.naranjo.kristian.pokemonandroid.R
import com.naranjo.kristian.pokemonandroid.datastore.Pokemon
import com.naranjo.kristian.pokemonandroid.ui.base.BaseListAdapter
import com.naranjo.kristian.pokemonandroid.ui.base.BaseViewHolder
import com.naranjo.kristian.pokemonandroid.ui.widgets.PokedexEntryBackground

class PokedexAdapter : BaseListAdapter<Pokemon, String>(Pokemon::name) {
    companion object {
        private const val VIEW_TYPE_POKEMON = R.layout.pokemon_row_item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Pokemon> =
        when (viewType) {
            VIEW_TYPE_POKEMON -> PokemonViewHolder(parent, viewType)
            else -> throw IllegalArgumentException()
        }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is Pokemon -> VIEW_TYPE_POKEMON
            else -> 0
        }
}

class PokemonViewHolder(parent: ViewGroup, layoutResId: Int) : BaseViewHolder<Pokemon>(layoutResId, parent) {
    lateinit var name: TextView

    override fun initViews() {
        itemView.background = PokedexEntryBackground()
        name = itemView.findViewById(R.id.name)
    }

    override fun bindData(data: Pokemon) {
        name.text = data.name
    }
}
