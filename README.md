# TrafficAnalysis

![录屏](/screens/1.png)
![录屏](/screens/2.png)
![录屏](/screens/3.png)
![录屏](/screens/4.png)
![录屏](/screens/5.png)

## 集成使用（远程依赖或aar依赖）

1.添加依赖
```

implementation ’com.jew.tech.android:traffic-analysis:1.0.0-SNAPSHOT‘ 

```

2.application中初始化

```

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        TASDKInstaller.install(this,true)
    }
}

```

3.开始监控

```

 TASDK.with(applicationContext).startCapture(this) { apps -> } //activity中调用

```

4.停止监控

```

TASDK.with(applicationContext).stopCapture()

```

## 混淆

```

-keep class com.jew.traffic_analysis.**{*;}

```

