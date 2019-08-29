package com.example.rehabapp

import java.util.concurrent.LinkedBlockingQueue

interface QueueHandOuter {

    fun giveMeTheAccQueue(): LinkedBlockingQueue<AccFrame>
    fun giveMeTheEmgQueue(): LinkedBlockingQueue<EmgFrame>

}