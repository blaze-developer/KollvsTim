package com.blazedeveloper.logging.inputs

import com.blazedeveloper.logging.structure.LogTable
import com.qualcomm.robotcore.hardware.Gamepad

fun Gamepad.writeToLog(table: LogTable, index: Int) {
    table.put("Gamepads/Joystick$index", toByteArray())
}

fun Gamepad.replayFromTable(table: LogTable, index: Int) {
    val bytes = table.get("Gamepads/Joystick$index", toByteArray())
    fromByteArray(bytes)
}