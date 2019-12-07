package com.naranjo.kristian.pokemonandroid.ui.details

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.naranjo.kristian.pokemonandroid.R
import com.naranjo.kristian.pokemonandroid.service.Pokemon
import com.naranjo.kristian.pokemonandroid.ui.base.BaseListAdapter
import com.naranjo.kristian.pokemonandroid.ui.base.BaseViewHolder
import com.naranjo.kristian.pokemonandroid.ui.widgets.LinearLayoutMarginItemDecoration

class PokemonDetailsAdapter : BaseListAdapter<Pokemon, String>(Pokemon::name) {
    companion object {
        private const val VIEW_TYPE_POKEMON = R.layout.pokemon_details_item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewTypeOrdinal: Int): BaseViewHolder<Pokemon> =
        when (viewTypeOrdinal) {
            VIEW_TYPE_POKEMON -> PokemonDetailsViewHolder(parent, viewTypeOrdinal)
            else -> throw IllegalArgumentException()
        }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is Pokemon -> VIEW_TYPE_POKEMON
            else -> 0
        }
}

class PokemonDetailsViewHolder(parent: ViewGroup, layoutResId: Int) :
    BaseViewHolder<Pokemon>(layoutResId, parent) {
    private val number: TextView = itemView.findViewById(R.id.details_number)
    private val name: TextView = itemView.findViewById(R.id.details_name)
    private val image: ImageView = itemView.findViewById(R.id.details_image)
    private val types: RecyclerView = itemView.findViewById(R.id.details_types)
    private val weaknesses: RecyclerView = itemView.findViewById(R.id.details_weaknesses)

    init {
        val marginItemDecoration = LinearLayoutMarginItemDecoration(
            itemView.context.resources.getDimension(R.dimen.details_types_spacing).toInt(),
            LinearLayoutMarginItemDecoration.Orientation.HORIZONTAL
        )
        types.addItemDecoration(marginItemDecoration)
        weaknesses.addItemDecoration(marginItemDecoration)
    }

    override fun bindData(data: Pokemon) {
        name.text = data.name
        number.text = data.number
        image.load(data.imageUrl)

        types.adapter = PokemonTypesAdapter().apply { submitList(data.types.map { TypeOnly(it) }) }
        weaknesses.adapter = PokemonTypesAdapter().apply {
            submitList(
                Pokemon.Type.calculateWeaknesses(data.types).map {
                    TypeEffectivenessItem(it.key, it.value)
                }
            )
        }
    }
}
