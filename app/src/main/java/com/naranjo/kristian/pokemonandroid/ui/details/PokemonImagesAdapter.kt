package com.naranjo.kristian.pokemonandroid.ui.details

import android.view.ViewGroup
import coil.api.load
import com.naranjo.kristian.pokemonandroid.R
import com.naranjo.kristian.pokemonandroid.databinding.PokemonImageItemBinding
import com.naranjo.kristian.pokemonandroid.ui.base.BaseListAdapter
import com.naranjo.kristian.pokemonandroid.ui.base.BaseViewHolder

class PokemonImagesAdapter : BaseListAdapter<PokemonImage, String>(PokemonImage::url) {
    companion object {
        private const val VIEW_TYPE_POKEMON_IMAGE = R.layout.pokemon_image_item
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewTypeOrdinal: Int
    ): BaseViewHolder<PokemonImage> =
        when (viewTypeOrdinal) {
            VIEW_TYPE_POKEMON_IMAGE -> PokemonImageViewHolder(parent, viewTypeOrdinal)
            else -> throw IllegalArgumentException()
        }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is PokemonImage -> VIEW_TYPE_POKEMON_IMAGE
            else -> 0
        }
}

class PokemonImageViewHolder(parent: ViewGroup, layoutResId: Int) :
    BaseViewHolder<PokemonImage>(layoutResId, parent) {
    private val binding = PokemonImageItemBinding.bind(itemView)

    override fun bindData(data: PokemonImage) {
        binding.detailsImage.load(data.url)
    }
}

data class PokemonImage(val url: String)
