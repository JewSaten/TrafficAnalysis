package com.example.traffic_analysis

import com.bin.david.form.annotation.SmartColumn
import com.bin.david.form.annotation.SmartTable

@SmartTable(name = "抓包记录表") class CapturePacket(
    @SmartColumn(id = 1, name = "No.", fixed = true) val number:Long,
    @SmartColumn(id = 2, name = "Time") val arrivalTime:String,
    @SmartColumn(id = 3, name = "Source") val source:String,
    @SmartColumn(id = 4, name = "Destination") val destination:String,
    @SmartColumn(id = 5, name = "Protocol") val protocol:String,
    @SmartColumn(id = 6, name = "Length") val length:Int,
    @SmartColumn(id = 7, name = "Info") val info:String,
    val detail:String
)