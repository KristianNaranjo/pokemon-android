package com.naranjo.kristian.pokemonandroid.ui.widgets

import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable

class PokedexEntryBackground : GradientDrawable() {
    companion object {
        private const val ANGLE_OFFSET = 10f
    }

    private val paint = Paint()

    override fun draw(canvas: Canvas) {
        val width = bounds.width().toFloat()
        val height = bounds.height().toFloat()

        paint.color = Color.RED
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.isAntiAlias = true

        val path = Path()
        path.moveTo(0f,0f)
        path.lineTo(width/2 - ANGLE_OFFSET, 0f)
        path.lineTo(width/2 + ANGLE_OFFSET, height)
        path.lineTo(0f, height)
        path.close()
        canvas.drawPath(path, paint)

        paint.color = Color.BLACK
        val blackPath = Path()
        blackPath.moveTo(width/2 - ANGLE_OFFSET, 0f)
        blackPath.lineTo(width/2 + ANGLE_OFFSET, height)
        blackPath.lineTo(width, height)
        blackPath.lineTo(width, 0f)
        blackPath.close()

        canvas.drawPath(blackPath, paint)
    }

    override fun setAlpha(p0: Int) {
    }

    override fun getOpacity(): Int = PixelFormat.OPAQUE

    override fun setColorFilter(p0: ColorFilter?) {
    }
}