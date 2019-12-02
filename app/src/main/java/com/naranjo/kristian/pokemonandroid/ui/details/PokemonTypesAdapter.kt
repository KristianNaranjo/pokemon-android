package com.naranjo.kristian.pokemonandroid.ui.details

import android.graphics.PorterDuff
import android.view.ViewGroup
import android.widget.TextView
import com.naranjo.kristian.pokemonandroid.R
import com.naranjo.kristian.pokemonandroid.datastore.Pokemon
import com.naranjo.kristian.pokemonandroid.ui.base.BaseListAdapter
import com.naranjo.kristian.pokemonandroid.ui.base.BaseViewHolder

class PokemonTypesAdapter : BaseListAdapter<Pokemon.Type, String>(Pokemon.Type::name) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Pokemon.Type> =
        when (viewType) {
            R.layout.pokemon_type_item -> PokemonTypeViewHolder(parent, viewType)
            else -> throw IllegalArgumentException()
        }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is Pokemon.Type -> R.layout.pokemon_type_item
            else -> 0
        }
}

class PokemonTypeViewHolder(parent: ViewGroup, layoutResId: Int) :
    BaseViewHolder<Pokemon.Type>(layoutResId, parent) {
    lateinit var type: TextView

    override fun initViews() {
        type = itemView.findViewById(R.id.pokemon_type)
    }

    override fun bindData(data: Pokemon.Type) {
        type.text = data.name
        if (data.colorResId != 0) {
            type.background.setColorFilter(type.context.getColor(data.colorResId), PorterDuff.Mode.SRC_ATOP)
        }
    }
}
