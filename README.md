# TrafficAnalysis
##
![录屏](https://s19.aconvert.com/convert/p3r68-cdx67/t32cq-y14uz.gif)

##集成使用（远程依赖或aar依赖）

1.
’‘’
implementation ’com.jew.tech.android:traffic-analysis:1.0.0-SNAPSHOT‘ 

‘’‘

2.application中初始化

’‘’
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        TASDKInstaller.install(this,true)
    }
}
‘’‘

3.开始监控

'''
 TASDK.with(applicationContext).startCapture(this) { apps -> } //activity中调用

'''

4.停止监控

’‘’
TASDK.with(applicationContext).stopCapture()

‘’‘

##混淆

'''
-keep class com.jew.traffic_analysis.**{*;}
'''

