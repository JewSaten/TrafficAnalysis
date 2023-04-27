package com.example.traffic_analysis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.traffic_analysis.databinding.ActivityMainBinding
import com.jew.traffic_analysis.ext.Apps
import com.jew.traffic_analysis.ext.TASDK

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val cachedResults = mutableListOf<Apps>()

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val captureLogs = StringBuilder()
        TASDK.with(applicationContext).startCapture(
            this
        ) { apps ->
            cachedResults.addAll(apps)
            captureLogs.clear()
            val result = cachedResults.asReversed()
            result.forEach {
                captureLogs.append("${it.appName}，${it.lastTime}").append("\n")
                    .append("${it.url}，${it.traffic}").append("\n\n")
            }
            binding.tvCapture.text = captureLogs
        }
    }

    override fun onDestroy() {
        TASDK.with(applicationContext).stopCapture()
        super.onDestroy()
    }

}