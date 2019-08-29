package com.example.rehabapp

class EmgFrame(dataArray: UByteArray) {

    private var dataArray = UByteArray(4)
    private var rawEmg: Float = 0.0F
    private var filtredEmg: Float = 0.0F

    init {
        this.dataArray=dataArray
        rawEmg = ((dataArray[0].toInt() shl 8) or dataArray[1].toInt()).toFloat()
        filtredEmg = ((dataArray[2].toInt() shl 8) or dataArray[3].toInt()).toFloat()
    }

    fun getRawEmg(): Float{
        return rawEmg
    }

    fun getFiltredEmg(): Float{
        return filtredEmg
    }


}