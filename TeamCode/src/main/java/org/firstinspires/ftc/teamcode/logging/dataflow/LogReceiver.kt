package org.firstinspires.ftc.teamcode.logging.dataflow

import org.firstinspires.ftc.teamcode.logging.structure.LogTable
import kotlin.jvm.Throws

interface LogReceiver {
    fun start() {}
    fun stop() {}
    @Throws(InterruptedException::class)
    fun receive(table: LogTable)
}