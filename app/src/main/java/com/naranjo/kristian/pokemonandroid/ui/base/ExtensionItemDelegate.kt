package com.naranjo.kristian.pokemonandroid.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

typealias BaseItemDelegate<T> = ItemDelegate<T, BaseExtensionViewHolder<T>>

interface ItemDelegate<T, H : RecyclerView.ViewHolder> {
    fun itemType(): Class<out T>
    fun createViewHolder(parent: ViewGroup): H
    fun bindView(position: Int, item: T, holder: H)
    fun bindView(position: Int, item: T, holder: H, payloads: List<Any>) {}
}

class LayoutItemDelegate<T>(
    private val type: Class<out T>,
    @LayoutRes private val layoutId: Int
) : BaseItemDelegate<T> {

    override fun itemType() = type

    override fun createViewHolder(parent: ViewGroup) = BaseExtensionViewHolder<T>(
        LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    )

    override fun bindView(position: Int, item: T, holder: BaseExtensionViewHolder<T>) {
        holder.holdItem = item
    }

    override fun bindView(
        position: Int,
        item: T,
        holder: BaseExtensionViewHolder<T>,
        payloads: List<Any>
    ) {
        holder.holdItem = item
    }
}

class CreateItemDelegate<T>(
    private val origin: BaseItemDelegate<T>,
    private val createBlock: BaseExtensionViewHolder<T>.() -> Unit
) : BaseItemDelegate<T> by origin {
    override fun createViewHolder(parent: ViewGroup) =
        origin.createViewHolder(parent).apply(createBlock)
}

class BindItemDelegate<T>(
    private val origin: BaseItemDelegate<T>,
    private val bindBlock: BaseExtensionViewHolder<T>.(T) -> Unit
) : BaseItemDelegate<T> by origin {
    override fun bindView(position: Int, item: T, holder: BaseExtensionViewHolder<T>) {
        origin.bindView(position, item, holder)
        holder.holdItem?.let { holder.bindBlock(it) }
    }
}

class BindPayloadsItemDelegate<T>(
    private val origin: BaseItemDelegate<T>,
    private val bindBlock: BaseExtensionViewHolder<T>.(T, List<Any>) -> Unit
) : BaseItemDelegate<T> by origin {

    override fun bindView(
        position: Int,
        item: T,
        holder: BaseExtensionViewHolder<T>,
        payloads: List<Any>
    ) {
        origin.bindView(position, item, holder, payloads)
        holder.holdItem?.let { holder.bindBlock(it, payloads) }
    }
}

class ClickableItemDelegate<T>(
    private val origin: BaseItemDelegate<T>,
    private val onClick: (T) -> Unit
) : BaseItemDelegate<T> by origin {
    override fun createViewHolder(parent: ViewGroup) =
        origin.createViewHolder(parent).apply {
            itemView bindClick onClick
        }
}

class ClickableIndexedItemDelegate<T>(
    private val origin: BaseItemDelegate<T>,
    private val clickListener: (T, Int) -> Unit
) : BaseItemDelegate<T> by origin {
    override fun createViewHolder(parent: ViewGroup) = origin.createViewHolder(parent).apply {
        itemView.setOnClickListener { holdItem?.let { clickListener(it, adapterPosition) } }
    }
}

inline fun <reified T> itemDelegate(@LayoutRes layoutId: Int) =
    LayoutItemDelegate(T::class.java, layoutId)

fun <T> BaseItemDelegate<T>.create(block: BaseExtensionViewHolder<T>.() -> Unit) =
    CreateItemDelegate(this, block)

fun <T> BaseItemDelegate<T>.bind(block: BaseExtensionViewHolder<T>.(item: T) -> Unit) =
    BindItemDelegate(this, block)

fun <T> BaseItemDelegate<T>.bindPayloads(block: BaseExtensionViewHolder<T>.(item: T, payloads: List<Any>) -> Unit) =
    BindPayloadsItemDelegate(this, block)

fun <T> BaseItemDelegate<T>.click(clickListener: (T) -> Unit) =
    ClickableItemDelegate(this, clickListener)

fun <T> BaseItemDelegate<T>.clickIndexed(clickListener: (T, Int) -> Unit) =
    ClickableIndexedItemDelegate(this, clickListener)