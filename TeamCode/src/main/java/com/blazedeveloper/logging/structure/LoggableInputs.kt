package com.blazedeveloper.logging.structure

interface LoggableInputs {
    fun toLog(table: LogTable)
    fun fromLog(table: LogTable)
}