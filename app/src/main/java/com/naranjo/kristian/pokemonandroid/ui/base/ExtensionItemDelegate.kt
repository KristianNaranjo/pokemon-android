package com.naranjo.kristian.pokemonandroid.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

typealias BaseItemDelegate<T> = ItemDelegate<T, BaseExtensionViewHolder<T>>

interface ItemDelegate<I, H : RecyclerView.ViewHolder> {
    fun itemType(): Class<out I>
    fun createViewHolder(parent: ViewGroup): H
    fun bindView(position: Int, item: I, holder: H)
    fun bindView(position: Int, item: I, holder: H, payloads: List<Any>) {}
}

class LayoutItemDelegate<I>(
    private val type: Class<out I>,
    @LayoutRes private val layoutId: Int
) : BaseItemDelegate<I> {

    override fun itemType() = type

    override fun createViewHolder(parent: ViewGroup) = BaseExtensionViewHolder<I>(LayoutInflater.from(parent.context).inflate(layoutId, parent))

    override fun bindView(position: Int, item: I, holder: BaseExtensionViewHolder<I>) {
        holder.holdItem = item
    }

    override fun bindView(position: Int, item: I, holder: BaseExtensionViewHolder<I>, payloads: List<Any>) {
        holder.holdItem = item
    }
}

class CreateItemDelegate<I>(
    private val origin: BaseItemDelegate<I>,
    private val createBlock: BaseExtensionViewHolder<I>.() -> Unit
) : BaseItemDelegate<I> by origin {
    override fun createViewHolder(parent: ViewGroup) = origin.createViewHolder(parent).apply(createBlock)
}

class BindItemDelegate<I>(
    private val origin: BaseItemDelegate<I>,
    private val bindBlock: BaseExtensionViewHolder<I>.(I) -> Unit
) : BaseItemDelegate<I> by origin {
    override fun bindView(position: Int, item: I, holder: BaseExtensionViewHolder<I>) {
        origin.bindView(position, item, holder)
        holder.holdItem?.let { holder.bindBlock(it) }
    }
}

class BindPayloadsItemDelegate<I>(
    private val origin: BaseItemDelegate<I>,
    private val bindBlock: BaseExtensionViewHolder<I>.(I, List<Any>) -> Unit
) : BaseItemDelegate<I> by origin {

    override fun bindView(position: Int, item: I, holder: BaseExtensionViewHolder<I>, payloads: List<Any>) {
        origin.bindView(position, item, holder, payloads)
        holder.holdItem?.let { holder.bindBlock(it, payloads) }
    }
}

class ClickableItemDelegate<I>(
    private val origin: BaseItemDelegate<I>,
    private val onClick: (I) -> Unit
) : BaseItemDelegate<I> by origin {
    override fun createViewHolder(parent: ViewGroup) =
        origin.createViewHolder(parent).apply {
            itemView bindClick onClick
        }
}

class ClickableIndexedItemDelegate<I>(
    private val origin: BaseItemDelegate<I>,
    private val clickListener: (I, Int) -> Unit
) : BaseItemDelegate<I> by origin {
    override fun createViewHolder(parent: ViewGroup) = origin.createViewHolder(parent).apply {
        itemView.setOnClickListener { holdItem?.let { clickListener(it, adapterPosition) } }
    }
}

inline fun <reified T> itemDelegate(@LayoutRes layoutId: Int) = LayoutItemDelegate(T::class.java, layoutId)

fun <T> BaseItemDelegate<T>.create(block: BaseExtensionViewHolder<T>.() -> Unit) = CreateItemDelegate(this, block)

fun <T> BaseItemDelegate<T>.bind(block: BaseExtensionViewHolder<T>.(item: T) -> Unit) = BindItemDelegate(this, block)

fun <T> BaseItemDelegate<T>.bindPayloads(block: BaseExtensionViewHolder<T>.(item: T, payloads: List<Any>) -> Unit) = BindPayloadsItemDelegate(this, block)

fun <T> BaseItemDelegate<T>.click(clickListener: (T) -> Unit) = ClickableItemDelegate(this, clickListener)

fun <T> BaseItemDelegate<T>.clickIndexed(clickListener: (T, Int) -> Unit) = ClickableIndexedItemDelegate(this, clickListener)