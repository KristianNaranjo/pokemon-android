package com.naranjo.kristian.pokemonandroid.ui.base

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

open class BaseExtensionViewHolder<I>(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    var holdItem: I? = null

    //This is needed, in case we moved xml into one module, but define delegates in other module
    private val viewMap: MutableMap<Int, View> = HashMap()

    fun <T> bind(@IdRes id: Int) = viewMap.getOrPut(id) { itemView.findViewById(id) } as T

    inline infix fun View.bindClick(
        crossinline onClick: (I) -> Unit
    ) = setOnClickListener { holdItem?.let(onClick) }
}