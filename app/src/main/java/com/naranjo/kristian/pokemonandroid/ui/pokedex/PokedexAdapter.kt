package com.naranjo.kristian.pokemonandroid.ui.pokedex

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.api.load
import com.naranjo.kristian.pokemonandroid.R
import com.naranjo.kristian.pokemonandroid.datastore.Pokemon
import com.naranjo.kristian.pokemonandroid.ui.base.BaseListAdapter
import com.naranjo.kristian.pokemonandroid.ui.base.BaseViewHolder

class PokedexAdapter(private val onPokemonClickedListener: (Pokemon) -> Unit) :
    BaseListAdapter<Pokemon, String>(Pokemon::name) {
    companion object {
        private const val VIEW_TYPE_POKEMON = R.layout.pokemon_row_item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Pokemon> =
        when (viewType) {
            VIEW_TYPE_POKEMON -> PokemonViewHolder(onPokemonClickedListener,parent, viewType)
            else -> throw IllegalArgumentException()
        }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is Pokemon -> VIEW_TYPE_POKEMON
            else -> 0
        }
}

class PokemonViewHolder(private val onPokemonClickedListener: (Pokemon) -> Unit, parent: ViewGroup, layoutResId: Int) :
    BaseViewHolder<Pokemon>(layoutResId, parent) {
    lateinit var name: TextView
    lateinit var number: TextView
    lateinit var image: ImageView

    private var selectedPokemon: Pokemon? = null

    override fun initViews() {
        name = itemView.findViewById(R.id.name)
        number = itemView.findViewById(R.id.number)
        image = itemView.findViewById(R.id.image)

        itemView.setOnClickListener {
            selectedPokemon?.let(onPokemonClickedListener)
        }
    }

    override fun bindData(data: Pokemon) {
        selectedPokemon = data

        name.text = data.name
        number.text = data.number
        image.load(data.imageUrl)
    }
}
