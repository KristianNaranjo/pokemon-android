package com.naranjo.kristian.pokemonandroid.ui.pokedex

import android.view.ViewGroup
import coil.api.load
import com.naranjo.kristian.pokemonandroid.R
import com.naranjo.kristian.pokemonandroid.databinding.PokemonRowItemBinding
import com.naranjo.kristian.pokemonandroid.service.Pokemon
import com.naranjo.kristian.pokemonandroid.ui.base.BaseListAdapter
import com.naranjo.kristian.pokemonandroid.ui.base.BaseViewHolder

class PokedexAdapter(private val onPokemonClickedListener: (Pokemon) -> Unit) :
    BaseListAdapter<Pokemon, String>(Pokemon::name) {
    companion object {
        private const val VIEW_TYPE_POKEMON = R.layout.pokemon_row_item
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewTypeOrdinal: Int
    ): BaseViewHolder<Pokemon> =
        when (viewTypeOrdinal) {
            VIEW_TYPE_POKEMON -> PokemonViewHolder(
                onPokemonClickedListener,
                parent,
                viewTypeOrdinal
            )
            else -> throw IllegalArgumentException()
        }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is Pokemon -> VIEW_TYPE_POKEMON
            else -> 0
        }
}

class PokemonViewHolder(
    private val onPokemonClickedListener: (Pokemon) -> Unit,
    parent: ViewGroup,
    layoutResId: Int
) :
    BaseViewHolder<Pokemon>(layoutResId, parent) {
    private val binding = PokemonRowItemBinding.bind(itemView)

    private var selectedPokemon: Pokemon? = null

    init {
        binding.root.setOnClickListener {
            selectedPokemon?.let(onPokemonClickedListener)
        }
    }

    override fun bindData(data: Pokemon) {
        selectedPokemon = data

        with(binding) {
            name.text = data.name
            number.text = data.number
            image.load(data.imageUrl)
        }
    }
}
