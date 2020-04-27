package com.naranjo.kristian.pokemonandroid.ui.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListAdapter<T, IdentifierType>(getId: (T) -> IdentifierType) :
    ListAdapter<T, BaseViewHolder<T>>(BaseDiffUtil<T, IdentifierType>(getId)) {

    abstract override fun onCreateViewHolder(
        parent: ViewGroup,
        viewTypeOrdinal: Int
    ): BaseViewHolder<T>

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) =
        holder.bindData(getItem(position))
}

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    constructor (layoutResId: Int, parent: ViewGroup) : this(
        LayoutInflater.from(parent.context).inflate(
            layoutResId,
            parent,
            false
        )
    )

    abstract fun bindData(data: T)
}
