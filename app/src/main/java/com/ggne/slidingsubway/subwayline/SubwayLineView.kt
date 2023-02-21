package com.ggne.slidingsubway.subwayline

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes

class SubwayLineView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint().apply {
        strokeWidth = 20f
    }

    fun changeLineColor(@ColorInt color: Int) {
        paint.color = color
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        canvas.drawLine(0f, height * 0.28f, width.toFloat(), height * 0.28f, paint)
    }
}
