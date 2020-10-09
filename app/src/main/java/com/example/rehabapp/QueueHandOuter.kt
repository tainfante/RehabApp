package com.example.rehabapp

import java.util.concurrent.LinkedBlockingQueue

interface QueueHandOuter {

    fun giveMeTheAccQueue(): LinkedBlockingQueue<AccFrame>
    fun giveMeTheEmgQueue(): LinkedBlockingQueue<EmgFrame>
    fun giveMeTheAccQueuetoPlot(): LinkedBlockingQueue<AccFrame>
    fun giveMeTheEmgQueuetoPlot(): LinkedBlockingQueue<EmgFrame>
    fun ifEmgEnable(): Boolean
    fun ifAccEnable(): Boolean
    fun setEnable(enable:Boolean, type: Int)

}