package com.blazedeveloper.logging.dataflow

import com.blazedeveloper.logging.structure.LogTable

interface LogReceiver {
    fun start() {}
    fun stop() {}
    @Throws(InterruptedException::class)
    fun receive(table: LogTable)
}