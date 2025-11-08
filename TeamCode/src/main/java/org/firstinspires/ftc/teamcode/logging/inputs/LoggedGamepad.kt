package org.firstinspires.ftc.teamcode.logging.inputs

import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.logging.structure.LogTable

fun Gamepad.writeToLog(table: LogTable, index: Int) {
    table.put("Gamepads/Joystick$index", toByteArray())
}

fun Gamepad.replayFromTable(table: LogTable, index: Int) {
    val bytes = table.get("Gamepads/Joystick$index", toByteArray())
    fromByteArray(bytes)
}