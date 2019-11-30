package com.naranjo.kristian.pokemonandroid.ui.details

import android.view.ViewGroup
import android.widget.TextView
import com.naranjo.kristian.pokemonandroid.R
import com.naranjo.kristian.pokemonandroid.datastore.Type
import com.naranjo.kristian.pokemonandroid.ui.base.BaseListAdapter
import com.naranjo.kristian.pokemonandroid.ui.base.BaseViewHolder

class PokemonTypesAdapter : BaseListAdapter<Type, String>(Type::name) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Type> =
        when (viewType) {
            R.layout.pokemon_type_item -> PokemonTypeViewHolder(parent, viewType)
            else -> throw IllegalArgumentException()
        }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is Type -> R.layout.pokemon_type_item
            else -> 0
        }
}

class PokemonTypeViewHolder(parent: ViewGroup, layoutResId: Int) :
    BaseViewHolder<Type>(layoutResId, parent) {
    lateinit var type: TextView

    override fun initViews() {
        type = itemView.findViewById(R.id.pokemon_type)
    }

    override fun bindData(data: Type) {
        type.text = data.name
    }
}
