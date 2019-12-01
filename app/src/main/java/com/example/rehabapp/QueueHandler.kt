package com.example.rehabapp

import java.util.concurrent.LinkedBlockingQueue

class QueueHandler {

    private var accQueue: LinkedBlockingQueue<AccFrame> = LinkedBlockingQueue()
    private var emgQueue: LinkedBlockingQueue<EmgFrame> = LinkedBlockingQueue()
    private var accQueuetoPlot: LinkedBlockingQueue<AccFrame> = LinkedBlockingQueue()
    private var emgQueuetoPlot: LinkedBlockingQueue<EmgFrame> = LinkedBlockingQueue()
    private var enable=false

    fun getAccQueue():LinkedBlockingQueue<AccFrame>{
        return accQueue
    }

    fun getEmgQueue(): LinkedBlockingQueue<EmgFrame>{
        return emgQueue
    }

    fun getAccQueuetoPlot():LinkedBlockingQueue<AccFrame>{
        return accQueuetoPlot
    }

    fun getEmgQueuetoPlot(): LinkedBlockingQueue<EmgFrame>{
        return emgQueuetoPlot
    }
    fun ifEnable():Boolean{
        return enable
    }
    fun setEnable(enable:Boolean){
        this.enable=enable
    }



}