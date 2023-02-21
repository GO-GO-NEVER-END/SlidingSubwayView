package com.ggne.slidingsubway.stationcircle

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import com.ggne.slidingsubway.R

@SuppressLint("ResourceAsColor")
class StationCircleView : View, ValueAnimator.AnimatorUpdateListener {

    @ColorInt
    private var subwayColor: Int = 0
    private var circleState: CircleState = CircleState.IDLE
    private var maxCircleRadius: Float = 50f
    private var circleRadius: Float = maxCircleRadius * DOWNSCALE
    private var innerCircleRadius: Float = 0f

    private val valueAnimator: ValueAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 200
        addUpdateListener(this@StationCircleView)
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        getAttrs(attrs)
        init()
    }

    private fun getAttrs(attrs: AttributeSet) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.StationCircleView,
            0,
            0
        ).apply {
            try {
                subwayColor = getColor(R.styleable.StationCircleView_subwayCircleColor, R.color.subway_2)
                circleState = CircleState.values()[getInteger(R.styleable.StationCircleView_subwayCircleState, 1)]
                maxCircleRadius = getFloat(R.styleable.StationCircleView_subwayCircleRadius, 50f)
            } finally {
                recycle()
            }
        }
    }

    private fun init() {
        when (circleState) {
            CircleState.FOCUS -> {
                innerCircleRadius = maxCircleRadius * INNER_DOWNSCALE
                circleRadius = maxCircleRadius
            }
            CircleState.IDLE -> {
                innerCircleRadius = 0f
                circleRadius = maxCircleRadius * DOWNSCALE
            }
        }
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
        canvas.drawCircle(width / 2.toFloat(), height / 2.toFloat(), circleRadius, fillPaint)
        canvas.drawCircle(width / 2.toFloat(), height / 2.toFloat(), innerCircleRadius, whiteFillPaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
            }
        }
        return super.onTouchEvent(event)
    }

    fun changeCircleState(newState: CircleState) {
        circleState = newState
        valueAnimator.start()
    }

    fun changeCircleColor(@ColorRes colorId: Int) {
        subwayColor = context.getColor(colorId)
        fillPaint.color = subwayColor
        invalidate()
    }

    override fun onAnimationUpdate(animation: ValueAnimator) {
        val scale = animation.animatedValue as Float

        when (circleState) {
            CircleState.FOCUS -> {
                innerCircleRadius = (maxCircleRadius * INNER_DOWNSCALE) * scale
                circleRadius = maxCircleRadius * DOWNSCALE + maxCircleRadius * (1f - DOWNSCALE) * scale
            }
            CircleState.IDLE -> {
                innerCircleRadius = (maxCircleRadius * INNER_DOWNSCALE) * (1f - scale)
                circleRadius = maxCircleRadius - maxCircleRadius * (1f - DOWNSCALE) * scale
            }
        }

        invalidate()
    }

    companion object {
        private const val DOWNSCALE = 0.8f
        private const val INNER_DOWNSCALE = 0.7f
    }
}
