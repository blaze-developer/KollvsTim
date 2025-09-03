package org.firstinspires.ftc.teamcode.subsystems

import dev.nextftc.core.subsystems.Subsystem
import dev.nextftc.ftc.Gamepads
import dev.nextftc.hardware.driving.DifferentialTankDriverControlled
import dev.nextftc.hardware.impl.MotorEx

class Drive(leftName: String, rightName: String) : Subsystem {
    private val left = MotorEx(leftName)
    private val right = MotorEx(rightName)

    override val defaultCommand = DifferentialTankDriverControlled(
        left,
        right,
        Gamepads.gamepad1.leftStickY,
        Gamepads.gamepad1.rightStickY
    )
}