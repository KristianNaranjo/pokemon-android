package com.naranjo.kristian.pokemonandroid.ui.details

import android.graphics.PorterDuff
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import com.naranjo.kristian.pokemonandroid.R
import com.naranjo.kristian.pokemonandroid.service.Pokemon
import com.naranjo.kristian.pokemonandroid.service.Pokemon.Type.Companion.EFFECTIVE
import com.naranjo.kristian.pokemonandroid.service.Pokemon.Type.Companion.NO_EFFECT
import com.naranjo.kristian.pokemonandroid.service.Pokemon.Type.Companion.SUPER_EFFECTIVE
import com.naranjo.kristian.pokemonandroid.ui.base.BaseListAdapter
import com.naranjo.kristian.pokemonandroid.ui.base.BaseViewHolder

class PokemonTypesAdapter : BaseListAdapter<TypeItem, Pokemon.Type>(TypeItem::type) {

    enum class ViewType(val layoutResId: Int) {
        TYPE_ONLY(R.layout.pokemon_type_item),
        TYPE_EFFECTIVENESS(R.layout.pokemon_type_effectiveness_item),
        UNKNOWN(0);
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewTypeOrdinal: Int
    ): BaseViewHolder<TypeItem> =
        when (val viewType = ViewType.values()[viewTypeOrdinal]) {
            ViewType.TYPE_ONLY -> PokemonTypeViewHolder(
                parent,
                viewType.layoutResId
            ) as BaseViewHolder<TypeItem>
            ViewType.TYPE_EFFECTIVENESS -> PokemonTypeEffectivenessViewHolder(
                parent,
                viewType.layoutResId
            ) as BaseViewHolder<TypeItem>
            ViewType.UNKNOWN -> throw IllegalArgumentException()
        }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is TypeOnly -> ViewType.TYPE_ONLY.ordinal
            is TypeEffectivenessItem -> ViewType.TYPE_EFFECTIVENESS.ordinal
            else -> ViewType.UNKNOWN.ordinal
        }
}

class PokemonTypeViewHolder(parent: ViewGroup, layoutResId: Int) :
    BaseViewHolder<TypeOnly>(layoutResId, parent) {
    private val type: TextView = itemView.findViewById(R.id.pokemon_type)

    override fun bindData(data: TypeOnly) {
        val type = data.type
        this.type.text = type.name
        this.type.background.setColorFilter(
            this.type.context.getColor(type.colorResId),
            PorterDuff.Mode.SRC_ATOP
        )
    }
}

class PokemonTypeEffectivenessViewHolder(parent: ViewGroup, layoutResId: Int) :
    BaseViewHolder<TypeEffectivenessItem>(layoutResId, parent) {
    private val typeText: TextView = itemView.findViewById(R.id.type)
    private val effectivenessText: TextView = itemView.findViewById(R.id.effectiveness)

    override fun bindData(data: TypeEffectivenessItem) {
        val type = data.type
        typeText.text = type.name
        itemView.background.setColorFilter(
            itemView.context.getColor(type.colorResId),
            PorterDuff.Mode.SRC_ATOP
        )

        val effectiveness = data.effectiveness
        if ((effectiveness < EFFECTIVE || effectiveness > SUPER_EFFECTIVE) && effectiveness != NO_EFFECT) {
            effectivenessText.isVisible = true
            effectivenessText.text =
                itemView.resources.getString(R.string.effectiveness, effectiveness)
        } else {
            effectivenessText.isVisible = false
        }
    }
}

sealed class TypeItem(open val type: Pokemon.Type)

data class TypeOnly(override val type: Pokemon.Type) : TypeItem(type)

data class TypeEffectivenessItem(override val type: Pokemon.Type, val effectiveness: Float) :
    TypeItem(type)
