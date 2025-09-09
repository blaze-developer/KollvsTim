package org.firstinspires.ftc.teamcode.subsystems

import dev.nextftc.bindings.Range
import dev.nextftc.core.commands.utility.LambdaCommand
import dev.nextftc.ftc.ActiveOpMode
import dev.nextftc.ftc.Gamepads
import dev.nextftc.hardware.impl.MotorEx
import kotlin.math.pow

class Drive(private val leftName: String, private val rightName: String) : SubsystemBase() {
    private lateinit var left: MotorEx
    private lateinit var right: MotorEx

    override fun initialize() {
        left = MotorEx(leftName)
        right = MotorEx(rightName)
    }

    private fun LambdaCommand.stopAtEnd() = this.setStop {
        left.power = 0.0
        right.power = 0.0
    }

    fun tankDrive(leftPower: Range, rightPower: Range, scalar: Double = 1.0, easingPower: Int = 1) = run {
        left.power = -leftPower.get().pow(easingPower) * scalar
        right.power = -rightPower.get().pow(easingPower) * scalar

        with (ActiveOpMode.telemetry) {
            addData("Left Power", left.power)
            addData("Right power", right.power)
            update()
        }
    }.stopAtEnd()

    fun povDrive(throttle: Range, steering: Range, easingPower: Int = 1) = run {
        val adjustedThrottle = -throttle.get().pow(easingPower)
        val adjustedSteering = steering.get().pow(easingPower)

        left.power = adjustedThrottle + adjustedSteering
        right.power = adjustedThrottle - adjustedSteering

        with (ActiveOpMode.telemetry) {
            addData("ThrottleRaw", throttle.get())
            addData("SteeringRaw", steering.get())
            addData("ThrottleAdjusted", adjustedThrottle)
            addData("SteeringAdjusted", adjustedSteering)
            addData("LeftPower", left.power)
            addData("RightPower", right.power)
            update()
        }
    }.stopAtEnd()

    fun runPower(leftPower: Double, rightPower: Double) = runOnce {
        left.power = leftPower
        right.power = rightPower
    }.stopAtEnd()

    fun runPov(throttle: Double, steering: Double) = runOnce {
        left.power = throttle + steering
        right.power = throttle - steering
    }.stopAtEnd()

    val stop = runOnce {
        left.power = 0.0
        right.power = 0.0
    }

    override val defaultCommand = povDrive(
        Gamepads.gamepad1.leftStickY,
        Gamepads.gamepad1.rightStickX,
        easingPower = 2
    )

}