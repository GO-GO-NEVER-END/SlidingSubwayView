package com.ggne.slidingsubway.stationcircle

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Interpolator
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import com.ggne.slidingsubway.R

class StationCircleView(context: Context, attrs: AttributeSet) :
    View(context, attrs),
    ValueAnimator.AnimatorUpdateListener {

    @ColorInt
    private val subwayColor: Int
    private var circleState: CircleState
    private val maxCircleRadius: Float
    private var circleRadius: Float

    private val valueAnimator: ValueAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 500
        addUpdateListener(this@StationCircleView)
    }

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
                maxCircleRadius = getFloat(R.styleable.StationCircleView_subwayCircleRadius, 25f)
            } finally {
                recycle()
            }
        }
        circleRadius = maxCircleRadius
    }

    private val strokePaint = Paint().apply {
        color = subwayColor
        style = Paint.Style.STROKE
        strokeWidth = 15f
    }

    private val fillPaint = Paint().apply {
        color = subwayColor
        style = Paint.Style.FILL
    }

    private val whiteFillPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.parseColor("#FFFFFF")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val diameter = (maxCircleRadius * 3f).toInt()
        setMeasuredDimension(diameter, diameter)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        when (circleState) {
            CircleState.FOCUS -> {
                canvas.drawCircle(width / 2.toFloat(), height / 2.toFloat(), circleRadius, strokePaint)
                canvas.drawCircle(width / 2.toFloat(), height / 2.toFloat(), circleRadius, whiteFillPaint)
            }
            CircleState.IDLE -> {
                fillPaint.color = subwayColor
                canvas.drawCircle(width / 2.toFloat(), height / 2.toFloat(), circleRadius, fillPaint)
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
        valueAnimator.start()
    }

    override fun onAnimationUpdate(animation: ValueAnimator) {
        val scale = animation.animatedValue as Float

        when (circleState) {
            CircleState.FOCUS -> {
                whiteFillPaint.alpha = (scale * 255).toInt()
                fillPaint.alpha = 255 - (scale * 255).toInt()
                circleRadius = (maxCircleRadius * DOWNSCALE) + 0.1f * scale
            }
            CircleState.IDLE -> {
                whiteFillPaint.alpha = 255 - (scale * 255).toInt()
                fillPaint.alpha = (scale * 255).toInt()
                circleRadius = maxCircleRadius - 0.1f * scale
            }
        }

        invalidate()
    }

    companion object {
        private const val DOWNSCALE = 0.9f
    }
}
