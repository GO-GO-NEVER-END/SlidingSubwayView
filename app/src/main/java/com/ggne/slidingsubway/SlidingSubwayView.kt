package com.ggne.slidingsubway

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
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
            focusItem()
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                binding.rvStation.smoothScrollToPosition(focusingItem-2)
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
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            addOnScrollListener(onScrollListener)
        }

        binding.subwayLineView.changeLineColor(subwayColor)
    }

    fun submitList(list: List<Station>) {
        stationListAdapter.submitList(list)
    }

    private fun focusItem() {
        val layoutManager = (binding.rvStation.layoutManager as LinearLayoutManager)
        val newFocusItem = layoutManager.findFirstVisibleItemPosition() + 2
        if (focusingItem == newFocusItem) return

        (binding.rvStation.findViewHolderForAdapterPosition(focusingItem) as StationListAdapter.StationViewHolder?)?.idle()
        focusingItem = newFocusItem
        (binding.rvStation.findViewHolderForAdapterPosition(focusingItem) as StationListAdapter.StationViewHolder?)?.focus()
    }



}
