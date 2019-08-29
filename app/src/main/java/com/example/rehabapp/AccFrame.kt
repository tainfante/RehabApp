package com.example.rehabapp

class AccFrame(dataArray: UByteArray) {

    private var dataArray = UByteArray(12)
    private var accX: Float = 0.0F
    private var accY: Float = 0.0F
    private var accZ: Float = 0.0F
    private var gyrX: Float = 0.0F
    private var gyrY: Float = 0.0F
    private var gyrZ: Float = 0.0F

    init{
       this.dataArray=dataArray
        accX = ((dataArray[0].toInt() shl 8) or dataArray[1].toInt()).toFloat()
        accY = ((dataArray[2].toInt() shl 8) or dataArray[3].toInt()).toFloat()
        accZ = ((dataArray[4].toInt() shl 8) or dataArray[5].toInt()).toFloat()
        gyrX = ((dataArray[6].toInt() shl 8) or dataArray[7].toInt()).toFloat()
        gyrY = ((dataArray[8].toInt() shl 8) or dataArray[9].toInt()).toFloat()
        gyrZ = ((dataArray[10].toInt() shl 8) or dataArray[11].toInt()).toFloat()
    }

    fun getAccX(): Float {
        return accX
    }

    fun getAccY(): Float {
        return accY
    }

    fun getAccZ(): Float {
        return accZ
    }

    fun getGyrX(): Float {
        return gyrX
    }

    fun getGyrY(): Float {
        return gyrY
    }

    fun getGyrZ(): Float {
        return gyrZ
    }

}