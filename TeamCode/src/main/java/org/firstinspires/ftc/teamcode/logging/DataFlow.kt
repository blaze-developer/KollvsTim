package org.firstinspires.ftc.teamcode.logging

interface LogReceiver {
    fun start()
    fun stop()
    fun process(table: LogTable)
}

interface LogSource {
    fun start()
    fun stop()
    fun getNext()
    operator fun invoke() = getNext()
}