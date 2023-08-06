package com.example.traffic_analysis

import android.app.Application
import com.scwang.smart.refresh.layout.SmartRefreshLayout

class App : Application(){
    companion object {
        init {
            SmartRefreshLayout.setDefaultRefreshInitializer { _, layout ->
                layout.setEnableLoadMoreWhenContentNotFull(false)
                layout.setEnableFooterFollowWhenNoMoreData(true)
            }
        }
    }
}