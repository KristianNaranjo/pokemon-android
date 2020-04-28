package com.naranjo.kristian.pokemonandroid.ui.base

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

open class DelegatesAdapter<T : Any>(
    getId: (T) -> Any,
    vararg delegates: ItemDelegate<out T, *>
) : ListAdapter<T, RecyclerView.ViewHolder>(BaseDiffUtil<T, Any>(getId)) {

    private val delegatesIndexMap: Map<Class<*>, Int>
    private val delegatesList: List<ItemDelegate<T, RecyclerView.ViewHolder>>

    init {
        val map = mutableMapOf<Class<*>, Int>()
        delegates.forEachIndexed { index, delegate ->
            if (map.put(delegate.itemType(), index) != null) {
                throw IllegalArgumentException()
            }
        }

        delegatesIndexMap = map
        delegatesList = delegates.map {
            @Suppress("UNCHECKED_CAST")
            it as? ItemDelegate<T, RecyclerView.ViewHolder>
                ?: throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int =
        delegatesIndexMap[getItem(position)::class.java]
            ?: throw IllegalStateException(
                "There is no delegate for item with type: ${getItem(
                    position
                )::class.java}"
            )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        delegatesList[viewType].createViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegatesList[getItemViewType(position)].bindView(position, getItem(position), holder)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            delegatesList[getItemViewType(position)].bindView(
                position,
                getItem(position),
                holder,
                payloads
            )
        }
    }
}