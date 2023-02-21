package com.ggne.slidingsubway

import android.content.Context
import android.util.AttributeSet
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
            focusing()
        }
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

    private fun focusing() {
        val centerPosition =
            (binding.rvStation.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() + 2

        (binding.rvStation.findViewHolderForAdapterPosition(centerPosition) as StationListAdapter.StationViewHolder?)?.focus()

        for (i in 1..2) {
            (binding.rvStation.findViewHolderForAdapterPosition(centerPosition - i) as StationListAdapter.StationViewHolder?)?.idle()
            (binding.rvStation.findViewHolderForAdapterPosition(centerPosition + i) as StationListAdapter.StationViewHolder?)?.idle()
        }
    }
}
