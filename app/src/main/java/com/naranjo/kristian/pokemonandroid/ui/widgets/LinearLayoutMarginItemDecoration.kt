package com.naranjo.kristian.pokemonandroid.ui.widgets

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LinearLayoutMarginItemDecoration(
    private val spaceHeight: Int,
    private val orientation: Orientation
) :
    RecyclerView.ItemDecoration() {
    sealed class Orientation {
        object HORIZONTAL : Orientation()
        object VERTICAL : Orientation()
    }

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            val adapterPosition = parent.getChildAdapterPosition(view)
            if ((adapterPosition == 0 && orientation is Orientation.VERTICAL) || orientation is Orientation.HORIZONTAL) {
                top = spaceHeight
            }
            if ((adapterPosition == 0 && orientation is Orientation.HORIZONTAL) || orientation is Orientation.VERTICAL) {
                left = spaceHeight
            }
            right = spaceHeight
            bottom = spaceHeight
        }
    }
}