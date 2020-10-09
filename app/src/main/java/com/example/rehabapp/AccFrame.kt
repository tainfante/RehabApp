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
            if(accX.and(32768) == 32768){
                accX=-(accX.inv().and(65535)-1)
            }
        accY = (dataArray[2].toInt() shl 8) or dataArray[3].toInt()
        if(accY.and(32768) == 32768){
            accY=-(accY.inv().and(65535)-1)
        }
        accZ = (dataArray[4].toInt() shl 8) or dataArray[5].toInt()
        if(accZ.and(32768) == 32768){
            accZ=-(accZ.inv().and(65535)-1)
        }
        gyrX = (dataArray[6].toInt() shl 8) or dataArray[7].toInt()
        if(gyrX.and(32768) == 32768){
            gyrX=-(gyrX.inv().and(65535)-1)
        }
        gyrY = (dataArray[8].toInt() shl 8) or dataArray[9].toInt()
        if(gyrY.and(32768) == 32768){
            gyrY=-(gyrY.inv().and(65535)-1)
        }
        gyrZ = (dataArray[10].toInt() shl 8) or dataArray[11].toInt()
        if(gyrZ.and(32768) == 32768){
            gyrZ=-(gyrZ.inv().and(65535)-1)
        }
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