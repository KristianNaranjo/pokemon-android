package com.naranjo.kristian.pokemonandroid.ui.widgets

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val spaceHeight: Int, private val orientation: Orientation) :
        RecyclerView.ItemDecoration() {
    enum class Orientation {
        HORIZONTAL,
        VERTICAL
    }

    override fun getItemOffsets(
            outRect: Rect, view: View,
            parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            val adapterPosition = parent.getChildAdapterPosition(view)
            if (adapterPosition == 0 && orientation == Orientation.VERTICAL || orientation == Orientation.HORIZONTAL) {
                top = spaceHeight
            }
            if (adapterPosition == 0 && orientation == Orientation.HORIZONTAL || orientation == Orientation.VERTICAL) {
                left = spaceHeight
            }
            right = spaceHeight
            bottom = spaceHeight
        }
    }
}