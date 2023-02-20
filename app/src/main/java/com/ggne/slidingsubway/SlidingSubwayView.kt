package com.ggne.slidingsubway

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.ggne.slidingsubway.databinding.SlidingSubwayViewBinding

class SlidingSubwayView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    @ColorInt
    private var subwayColor: Int

    private var binding: SlidingSubwayViewBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.sliding_subway_view,
        this,
        false
    )

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SlidingSubwayView,
            0,
            0
        ).apply {
            try {
                subwayColor = getColor(R.styleable.SlidingSubwayView_subwayColor, 0)
            } finally {
                recycle()
            }
        }

        addView(binding.root)
    }

    private val paint = Paint().apply {
        color = subwayColor
        strokeWidth = 20f
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        canvas.drawLine(0f, height * 0.7f, width.toFloat(), height * 0.7f, paint)
    }
}
