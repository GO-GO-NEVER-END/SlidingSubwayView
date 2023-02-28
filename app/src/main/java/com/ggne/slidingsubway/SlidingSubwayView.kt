package com.ggne.slidingsubway

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ggne.slidingsubway.databinding.SlidingSubwayViewBinding

class SlidingSubwayView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    @ColorInt
    private var subwayColor: Int

    private var binding: SlidingSubwayViewBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.sliding_subway_view,
        this,
        false
    )

    private val stationListAdapter = StationListAdapter()
    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            focusStation()

            when (newState) {
                RecyclerView.SCROLL_STATE_DRAGGING -> {
                    (binding.rvStation.findViewHolderForAdapterPosition(focusingItem) as StationListAdapter.StationViewHolder?)?.idle()
                }
                RecyclerView.SCROLL_STATE_IDLE -> {
                    playCircleAnimation()
                    binding.rvStation.smoothScrollToPosition(focusingItem - 2)
                }
            }
        }
    }

    private var screenWidth: Int = 0
    private var focusingItem: Int = 0
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        screenWidth = width
    }

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

        binding.rvStation.apply {
            adapter = stationListAdapter
            layoutManager = SlidingSubwayLinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            addOnScrollListener(onScrollListener)
        }

        binding.subwayLineView.changeLineColor(subwayColor)
    }

    fun submitList(list: List<Station>) {
        stationListAdapter.submitList(list)
    }

    private fun focusStation() {
        val layoutManager = (binding.rvStation.layoutManager as SlidingSubwayLinearLayoutManager)
        val newFocusItem = layoutManager.findFirstVisibleItemPosition() + 2
        if (focusingItem == newFocusItem) return

        focusingItem = newFocusItem
    }

    private fun playCircleAnimation() {
        for (i in 1..2) {
            (binding.rvStation.findViewHolderForAdapterPosition(focusingItem - i) as StationListAdapter.StationViewHolder?)?.idle()
            (binding.rvStation.findViewHolderForAdapterPosition(focusingItem + i) as StationListAdapter.StationViewHolder?)?.idle()
        }
        (binding.rvStation.findViewHolderForAdapterPosition(focusingItem) as StationListAdapter.StationViewHolder?)?.focus()
    }
}
