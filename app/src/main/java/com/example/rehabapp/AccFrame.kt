package com.example.rehabapp

class AccFrame(dataArray: UByteArray) {

    private var dataArray = UByteArray(12)
    private var accX: Int = 0
    private var accY: Int = 0
    private var accZ: Int = 0
    private var gyrX: Int = 0
    private var gyrY: Int = 0
    private var gyrZ: Int = 0

    init{
       this.dataArray=dataArray
        accX = (dataArray[0].toInt() shl 8) or dataArray[1].toInt()
        accY = (dataArray[2].toInt() shl 8) or dataArray[3].toInt()
        accZ = (dataArray[4].toInt() shl 8) or dataArray[5].toInt()
        gyrX = (dataArray[6].toInt() shl 8) or dataArray[7].toInt()
        gyrY = (dataArray[8].toInt() shl 8) or dataArray[9].toInt()
        gyrZ = (dataArray[10].toInt() shl 8) or dataArray[11].toInt()
    }

    fun getAccX(): Int {
        return accX
    }

    fun getAccY(): Int {
        return accY
    }

    fun getAccZ(): Int {
        return accZ
    }

    fun getGyrX(): Int {
        return gyrX
    }

    fun getGyrY(): Int {
        return gyrY
    }

    fun getGyrZ(): Int {
        return gyrZ
    }

}