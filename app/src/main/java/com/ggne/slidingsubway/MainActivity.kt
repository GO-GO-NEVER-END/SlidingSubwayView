package com.ggne.slidingsubway

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ggne.slidingsubway.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val stationList = listOf(
        Station("보라매"),
        Station("신대방삼거리"),
        Station("장승배기"),
        Station("상도"),
        Station("숭실대입구"),
        Station("남성"),
        Station("이수"),
        Station("내방"),
        Station("고속터미널"),
        Station("반포"),
        Station("논현"),
        Station("학동"),
        Station("강남구청"),
        Station("청담"),
        Station("뚝섬유원지"),
        Station("건대입구"),
        Station("어린이대공원"),
        Station("군자"),
        Station("중곡"),
        Station("용마산"),
        Station("사가정"),
        Station("면목"),
        Station("상봉"),
        Station("중화")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        binding.slidingSubwayView.submitList(stationList)
    }
}
