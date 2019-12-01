package com.example.rehabapp

class EmgFrame(dataArray: UByteArray) {

    private var dataArray = UByteArray(4)
    private var rawEmg: Int = 0
    private var filtredEmg: Int = 0

    init {
        this.dataArray=dataArray
        rawEmg = (dataArray[0].toInt() shl 8) or dataArray[1].toInt()
        filtredEmg = (dataArray[2].toInt() shl 8) or dataArray[3].toInt()
    }

    fun getRawEmg(): Int{
        return rawEmg
    }

    fun getFiltredEmg(): Int{
        return filtredEmg
    }


}