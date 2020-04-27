package com.naranjo.kristian.pokemonandroid.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

open class BaseExtensionViewHolder<I>(override val containerView: View) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    var holdItem: I? = null

    inline infix fun View.bindClick(
        crossinline onClick: (I) -> Unit
    ) = setOnClickListener { holdItem?.let(onClick) }
}