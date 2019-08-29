package com.example.rehabapp

import java.util.concurrent.LinkedBlockingQueue

class QueueHandler {

    private var accQueue: LinkedBlockingQueue<AccFrame> = LinkedBlockingQueue()
    private var emgQueue: LinkedBlockingQueue<EmgFrame> = LinkedBlockingQueue()

    fun getAccQueue():LinkedBlockingQueue<AccFrame>{
        return accQueue
    }

    fun getEmgQueue(): LinkedBlockingQueue<EmgFrame>{
        return emgQueue
    }



}