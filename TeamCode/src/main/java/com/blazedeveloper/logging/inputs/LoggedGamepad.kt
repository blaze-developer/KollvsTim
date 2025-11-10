package com.blazedeveloper.logging.inputs

import com.blazedeveloper.logging.structure.LogTable
import com.qualcomm.robotcore.hardware.Gamepad

fun Gamepad.writeToTable(table: LogTable, index: Int) {
    table.put("LoggedGamepad/Gamepad$index", toByteArray())
}

fun Gamepad.replayFromTable(table: LogTable, index: Int) {
    val bytes = table.get("LoggedGamepad/Gamepad$index", toByteArray())
    fromByteArray(bytes)
}