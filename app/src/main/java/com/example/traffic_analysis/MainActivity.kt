package com.example.traffic_analysis

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.traffic_analysis.databinding.ActivityMainBinding
import com.jew.traffic_analysis.ext.Apps
import com.jew.traffic_analysis.ext.CaptureCallback
import com.jew.traffic_analysis.ext.State
import com.jew.traffic_analysis.ext.TAConfig
import com.jew.traffic_analysis.ext.TAManager
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission

private const val TAG = "com.example.main"
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val cachedResults = mutableListOf<Apps>()
    private val captureLogs = StringBuilder()
    private var mState = State.READY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AndPermission.with(this)
            .runtime()
            .permission(Permission.Group.STORAGE)
            .start()
        TAManager.init(this, TAConfig("Bsg3UATVQnepVcj", true))

        TAManager.statsCallback(
            this
        ) { stats ->
            binding.tvStatus.text = "已捕获%s".format(stats)
        }
        TAManager.captureCallback(this, object : CaptureCallback {
            override fun onCaptureResults(results: MutableList<Apps>) {
                cachedResults.addAll(results)
                captureLogs.clear()
                val result = cachedResults.asReversed()
                result.forEach {
                    captureLogs.append("${it.appName}，${it.lastTime}").append("\n")
                        .append("${it.url}，${it.traffic}").append("\n\n")
                }
                binding.tvCapture.text = captureLogs
            }

            override fun onSureAnalysis(context: Context, fpath: String?) {
                AnalysisActivity.start(context,fpath)
            }

            override fun onStateChanged(state: State) {
                mState = state
                Log.d(TAG,"State:$state")
                when (state) {
                    State.READY,State.STARTING -> {
                        cachedResults.clear()
                        captureLogs.clear()
                        binding.tvStatus.text = ""
                        binding.btn.text = "开始抓包"
                    }

                    State.RUNNING,State.STOPPING -> {
                        binding.btn.text = "停止抓包"
                    }
                }
            }

        })
        binding.btn.setOnClickListener {
            if(mState == State.READY || mState == State.STARTING){
                TAManager.startCapture(this)
            }else {
                TAManager.stopCapture()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        TAManager.registerCaptureCallback()
    }

    override fun onPause() {
        TAManager.unregisterCaptureCallback()
        super.onPause()
    }

}