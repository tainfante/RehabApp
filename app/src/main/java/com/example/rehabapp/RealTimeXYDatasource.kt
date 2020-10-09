package com.example.rehabapp

import java.util.*
import java.util.Observable

class RealTimeXYDatasource(queueHandOuter: QueueHandOuter, private val type: Int) : Runnable {

    private val notifier: MyObservable
    private var keepRunning = false
    private val ACCX = 0
    private val ACCY = 1
    private val ACCZ = 2
    private val GYRX = 3
    private val GYRY = 4
    private val GYRZ = 5
    private val RAWEMG = 0
    private val FILEMG = 1
    lateinit var accFrame: AccFrame
    lateinit var emgFrame: EmgFrame
    var accx: Int = 0
    var accy: Int = 0
    var accz: Int = 0
    var gyrx: Int = 0
    var gyry: Int = 0
    var gyrz: Int = 0
    var rawemg: Short = 0
    var filemg: Short = 0
    private val SAMPLE_SIZE = 1000
    private var queueHandOuter: QueueHandOuter? = null

    init {
        this.queueHandOuter = queueHandOuter
        notifier = MyObservable()
    }

    fun stopThread() {
        keepRunning = false
    }

    override fun run() {
        try {
            keepRunning = true
            queueHandOuter!!.setEnable(true, type)

            if (type == 0) {
                while (keepRunning) {

                    emgFrame = queueHandOuter!!.giveMeTheEmgQueuetoPlot().take()
                    rawemg = emgFrame.getRawEmg()
                    filemg = emgFrame.getFiltredEmg()

                    notifier.notifyObservers()
                }
                queueHandOuter!!.setEnable(false, type)

            } else {
                while (keepRunning) {

                    accFrame = queueHandOuter!!.giveMeTheAccQueuetoPlot().take()
                    accx = accFrame.getAccX()
                    accy = accFrame.getAccY()
                    accz = accFrame.getAccZ()
                    gyrx = accFrame.getGyrX()
                    gyry = accFrame.getGyrY()
                    gyrz = accFrame.getGyrZ()

                    notifier.notifyObservers()
                }
                queueHandOuter!!.setEnable(false, type)
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun getItemCount(series: Int): Int {
        return SAMPLE_SIZE
    }

    fun getX(series: Int, index: Int): Number {
        if (index >= SAMPLE_SIZE) {
            throw IllegalArgumentException()
        }
        return index
    }

    fun getY(series: Int, index: Int): Number {
        if (index >= SAMPLE_SIZE) {
            throw IllegalArgumentException()
        }
        if(type==0){
            return when(series){
                RAWEMG -> rawemg
                FILEMG -> filemg

                else -> throw IllegalArgumentException()
            }
        } else {
            return when (series) {
                ACCX -> accx
                ACCY -> accy
                ACCZ -> accz
                GYRX -> gyrx
                GYRY -> gyry
                GYRZ -> gyrz

                else -> throw IllegalArgumentException()
            }
        }
    }

    fun addObserver(observer: Observer) {
        notifier.addObserver(observer)
    }

    fun removeObserver(observer: Observer) {
        notifier.deleteObserver(observer)
    }

    inner class MyObservable : Observable() {
        override fun notifyObservers() {
            setChanged()
            super.notifyObservers()
        }
    }

}