package org.firstinspires.ftc.teamcode.logging

interface LoggableInputs {
    fun toLog(table: LogTable)
    fun fromLog(table: LogTable)
}