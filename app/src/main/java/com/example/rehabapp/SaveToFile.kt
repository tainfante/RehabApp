package com.example.rehabapp

import android.content.Context
import java.io.File
import java.io.IOException

class SaveToFile(val context: Context, queueHandOuter: QueueHandOuter) {

    private var accfile: File? = null
    private var emgfile: File? = null
    private var directory: File? = null
    private var queueHandOuter: QueueHandOuter? = null

    init {
        this.queueHandOuter = queueHandOuter
    }

    fun createFile(filename:String){

        val path = context.getExternalFilesDir(null)
        directory = File(path, "DATA")
        val success = directory!!.mkdirs()
        accfile = File(directory, filename+"_acc.txt")
        emgfile = File(directory, filename+"_emg.txt")

    }
    fun saveAccToFile(){

        var accFrame: AccFrame
        var accx: Int
        var accy: Int
        var accz: Int
        var gyrx: Int
        var gyry: Int
        var gyrz: Int

        val saveAccDataThread = Thread(Runnable {
            while (true) {
                try {

                    accFrame=queueHandOuter!!.giveMeTheAccQueue().take()
                    accx=accFrame.getAccX()
                    accy=accFrame.getAccY()
                    accz=accFrame.getAccZ()
                    gyrx=accFrame.getGyrX()
                    gyry=accFrame.getGyrY()
                    gyrz=accFrame.getGyrZ()
                    accfile?.appendText("$accx $accy $accz $gyrx $gyry $gyrz\n\r")

                } catch (ex: IOException) {
                }
            }
        })
        saveAccDataThread.start()

    }
    fun saveEmgToFile(){

        var rawEmg: Short
        var filtredEmg: Short
        var emgFrame: EmgFrame

        val saveEmgDataThread = Thread(Runnable {
            while (true) {
                try {
                    emgFrame = queueHandOuter!!.giveMeTheEmgQueue().take()
                    rawEmg= emgFrame.getRawEmg()
                    filtredEmg=emgFrame.getFiltredEmg()
                    emgfile?.appendText("$rawEmg $filtredEmg\n\r")

                } catch (ex: IOException) {
                }
            }
        })
        saveEmgDataThread.start()

    }
}