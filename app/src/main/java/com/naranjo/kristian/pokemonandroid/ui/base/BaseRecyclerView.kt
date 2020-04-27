package com.naranjo.kristian.pokemonandroid.ui.base

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class BaseRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
    private var adapterDataObserver: AdapterDataObserver? = null

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)

        adapter?.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()

                scrollToPosition(0)
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                super.onItemRangeChanged(positionStart, itemCount)

                scrollToPosition(0)
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)

                scrollToPosition(0)
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)

                scrollToPosition(0)
            }
        })
    }

    override fun onDetachedFromWindow() {
        adapterDataObserver?.let {
            adapter?.unregisterAdapterDataObserver(it)
        }

        super.onDetachedFromWindow()
    }
}