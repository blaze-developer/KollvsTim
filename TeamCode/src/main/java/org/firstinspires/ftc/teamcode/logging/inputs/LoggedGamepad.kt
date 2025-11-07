package org.firstinspires.ftc.teamcode.logging.inputs

import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.component.Logger
import org.firstinspires.ftc.teamcode.logging.structure.LogTable
import org.firstinspires.ftc.teamcode.logging.structure.LoggableInputs

class LoggedGamepad(val gamepad: Gamepad, val index: Int) : Gamepad() {
    private val inputs = object : LoggableInputs {
        override fun toLog(table: LogTable) {
            val buttons = listOf(
                gamepad.touchpad_finger_2,
                gamepad.touchpad_finger_1,
                gamepad.right_stick_button,
                gamepad.left_stick_button,
                gamepad.start,
                gamepad.back,
                gamepad.right_bumper,
                gamepad.left_bumper,
                gamepad.y,
                gamepad.x,
                gamepad.b,
                gamepad.a
            )

            val buttonBits = buttons.fold(0) { acc, button -> (acc shl 1) + if (button) 1 else 0 }

            table.putV("ButtonCount", 12)
            table.putV("ButtonValues", buttonBits)
        }

        override fun fromLog(table: LogTable) {
            TODO("Not yet implemented")
        }
    }

    private fun LogTable.putV(name: String, value: Any) = put("Joystick$index/$name", value)

    fun periodic() {
        if (!Logger.hasReplaySource) {

        }
        Logger.processInputs("Gamepad$index", inputs)
    }
}