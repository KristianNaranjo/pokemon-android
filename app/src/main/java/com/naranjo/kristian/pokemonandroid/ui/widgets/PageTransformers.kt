package com.naranjo.kristian.pokemonandroid.ui.widgets

import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

object TranslationTransformer :
        (Int, Int, @ViewPager2.Orientation Int) -> ViewPager2.PageTransformer {
    override fun invoke(
        offsetPx: Int,
        marginPx: Int,
        @ViewPager2.Orientation orientation: Int
    ): ViewPager2.PageTransformer = ViewPager2.PageTransformer { page, position ->
        val offset = position * -(2 * offsetPx + marginPx)
        when (orientation) {
            ViewPager2.ORIENTATION_HORIZONTAL -> {
                page.translationX = offset
            }
            else -> {
                page.translationY = offset
            }
        }
    }
}

object AlphaTransformer : (Float) -> ViewPager2.PageTransformer {
    override fun invoke(alphaFactor: Float) = ViewPager2.PageTransformer { page, position ->
        page.alpha = 1 - abs(position) * alphaFactor
    }
}

object ScaleTransformer : (Float) -> ViewPager2.PageTransformer {
    override fun invoke(scaleFactor: Float) = ViewPager2.PageTransformer { page, position ->
        val positionScale = 1 - abs(position) * scaleFactor
        page.scaleX = positionScale
        page.scaleY = positionScale
    }
}
