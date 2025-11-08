package org.firstinspires.ftc.teamcode.logging.inputs

import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.logging.structure.LogTable

fun Gamepad.writeToLog(table: LogTable) {
    table.put("Joystick$gamepadId", toByteArray())
}

fun Gamepad.replayFromTable(table: LogTable) {
    val bytes = table.get("Joystick$gamepadId", toByteArray())
    fromByteArray(bytes)
}