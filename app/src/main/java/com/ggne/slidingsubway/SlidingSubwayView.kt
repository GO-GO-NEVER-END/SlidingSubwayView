package com.ggne.slidingsubway

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
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
        }

        binding.subwayLineView.changeLineColor(subwayColor)
    }

    fun submitList(list: List<Station>) {
        stationListAdapter.submitList(list)
    }
}
