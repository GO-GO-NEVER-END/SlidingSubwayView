package com.ggne.slidingsubway

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ggne.slidingsubway.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val stationList = listOf(
        Station("청담"),
        Station("뚝섬유원지"),
        Station("건대입구"),
        Station("어린이대공원"),
        Station("군자")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        binding.slidingSubwayView.submitList(stationList)
    }
}
