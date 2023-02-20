package com.ggne.slidingsubway.stationcircle

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import com.ggne.slidingsubway.R

class StationCircleView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    @ColorInt
    private val subwayColor: Int
    private var circleState: CircleState
    private val circleRadius: Float

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.StationCircleView,
            0,
            0
        ).apply {
            try {
                subwayColor = getColor(R.styleable.StationCircleView_subwayColor, 0)
                circleState = CircleState.values()[getInteger(R.styleable.StationCircleView_subwayCircleState, 1)]
                circleRadius = getFloat(R.styleable.StationCircleView_subwayCircleRadius, 25f)
            } finally {
                recycle()
            }
        }
    }

    private val strokePaint = Paint().apply {
        color = subwayColor
        style = Paint.Style.STROKE
        strokeWidth = 15f
    }

    private val fillPaint = Paint().apply {
        style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val diameter = (circleRadius * 3f).toInt()
        setMeasuredDimension(diameter, diameter)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        when (circleState) {
            CircleState.FOCUS -> {
                canvas.drawCircle(width / 2.toFloat(), height / 2.toFloat(), circleRadius, strokePaint)
                fillPaint.color = Color.parseColor("#FFFFFF")
                canvas.drawCircle(width / 2.toFloat(), height / 2.toFloat(), circleRadius, fillPaint)
            }
            CircleState.IDLE -> {
                fillPaint.color = subwayColor
                canvas.drawCircle(width / 2.toFloat(), height / 2.toFloat(), circleRadius * DOWNSCALE, fillPaint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                when (circleState) {
                    CircleState.FOCUS -> changeCircleState(CircleState.IDLE)
                    CircleState.IDLE -> changeCircleState(CircleState.FOCUS)
                }
            }
        }
        return super.onTouchEvent(event)
    }

    fun changeCircleState(newState: CircleState) {
        circleState = newState
        invalidate()
    }

    companion object {
        private const val DOWNSCALE = 0.9f
    }
}
