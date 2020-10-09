package com.example.rehabapp

import kotlin.experimental.or

@ExperimentalUnsignedTypes
class EmgFrame(dataArray: UByteArray) {

    private var dataArray = UByteArray(4)
    private var rawEmg: Short = 0
    private var filtredEmg: Short = 0

    init {
        this.dataArray=dataArray
        rawEmg = (dataArray[0].toInt() shl 8).toShort() or dataArray[1].toShort()
        filtredEmg = (dataArray[2].toInt() shl 8).toShort() or dataArray[3].toShort()
    }

    fun getRawEmg(): Short{
        return rawEmg
    }

    fun getFiltredEmg(): Short{
        return filtredEmg
    }


}