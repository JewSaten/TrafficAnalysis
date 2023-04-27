package com.example.traffic_analysis

import android.app.Application
import com.jew.traffic_analysis.ext.TASDKInstaller

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        TASDKInstaller.install(this,true)
    }
}