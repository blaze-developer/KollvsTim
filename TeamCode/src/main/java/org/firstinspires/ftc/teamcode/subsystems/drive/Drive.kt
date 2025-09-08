package org.firstinspires.ftc.teamcode.subsystems.drive

import dev.nextftc.bindings.Range
import dev.nextftc.ftc.ActiveOpMode
import dev.nextftc.ftc.Gamepads
import dev.nextftc.hardware.impl.MotorEx
import org.firstinspires.ftc.teamcode.subsystems.SubsystemBase
import kotlin.math.pow

class Drive(leftName: String, rightName: String) : SubsystemBase() {
    private val left = MotorEx(leftName)
    private val right = MotorEx(rightName)

    fun tankDrive(leftPower: Range, rightPower: Range, scalar: Double = 1.0, easingPower: Int = 1) = run {
        left.power = -leftPower.get().pow(easingPower) * scalar
        right.power = -rightPower.get().pow(easingPower) * scalar

        with (ActiveOpMode.telemetry) {
            addData("Left Power", left.power)
            addData("Right power", right.power)
            update()
        }
    }.setStop {
        left.power = 0.0
        right.power = 0.0
    }

    fun povDrive(throttle: Range, steering: Range, easingPower: Int = 1) = run {
        val adjustedThrottle = -throttle.get().pow(easingPower)
        val adjustedSteering = steering.get().pow(easingPower)

        left.power = adjustedThrottle + adjustedSteering
        right.power = adjustedThrottle - adjustedSteering

        with (ActiveOpMode.telemetry) {
            addData("ThrottleRaw", throttle.get())
            addData("SteeringRaw", steering.get())
            addData("LeftPower", left.power)
            addData("RightPower", right.power)
            update()
        }
    }.setStop {
        left.power = 0.0
        right.power = 0.0
    }

    override val defaultCommand = povDrive(
        Gamepads.gamepad1.leftStickY,
        Gamepads.gamepad1.rightStickX,
        easingPower = 2
    )

}