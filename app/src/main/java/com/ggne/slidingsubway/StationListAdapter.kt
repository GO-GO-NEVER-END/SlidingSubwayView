package com.ggne.slidingsubway

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ggne.slidingsubway.databinding.ItemStationBinding
import com.ggne.slidingsubway.stationcircle.CircleState

class StationListAdapter : ListAdapter<Station, StationListAdapter.StationViewHolder>(diffUtil) {

    inner class StationViewHolder(private val binding: ItemStationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(station: Station) {
            binding.station = station
            binding.stationCircleView.changeCircleColor(R.color.subway_7)
        }

        fun focus() {
            binding.stationCircleView.changeCircleState(CircleState.FOCUS)
        }

        fun idle() {
            binding.stationCircleView.changeCircleState(CircleState.IDLE)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        return StationViewHolder(
            ItemStationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Station>() {
            override fun areItemsTheSame(oldItem: Station, newItem: Station): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Station, newItem: Station): Boolean {
                return oldItem == newItem
            }
        }
    }
}
