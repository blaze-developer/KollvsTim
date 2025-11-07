package org.firstinspires.ftc.teamcode.logging.structure

interface LoggableInputs {
    fun toLog(table: LogTable)
    fun fromLog(table: LogTable)
}