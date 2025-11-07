package org.firstinspires.ftc.teamcode.logging.dataflow

import org.firstinspires.ftc.teamcode.logging.structure.LogTable

interface LogReceiver {
    fun start()
    fun stop()
    fun process(table: LogTable)
}