package org.firstinspires.ftc.teamcode.subsystems.drive

import dev.nextftc.bindings.Range
import dev.nextftc.ftc.Gamepads
import org.firstinspires.ftc.teamcode.logging.Logger
import org.firstinspires.ftc.teamcode.subsystems.SubsystemBase
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sign
import kotlin.math.sin
import kotlin.time.Duration

class Drive(private val io: DriveIO) : SubsystemBase() {
    private val inputs = DriveInputs()

    override fun periodic() {
        io.updateInputs(inputs)
        Logger.processInputs("Drive", inputs)
    }

    fun runFieldPowersCmd(fieldX: Double, fieldY: Double, fieldTheta: Double) = runOnce {
        runFieldPowers(fieldX, fieldY, fieldTheta)
    }.setIsDone { false }

    private fun runFieldPowers(fieldX: Double, fieldY: Double, fieldTheta: Double) {
        val heading = inputs.yawRads
        val robotX = -(fieldX * sin(-heading) + fieldY * cos(-heading))
        val robotY = fieldX * cos(-heading) - fieldY * sin(-heading)

        runRobotPowers(robotX, robotY, fieldTheta)
    }

    private fun runRobotPowers(robotX: Double, robotY: Double, robotTheta: Double) {
        val denominator =
            max(robotX.absoluteValue + robotY.absoluteValue + robotTheta.absoluteValue, 1.0)

        io.runPowers(
            fl = (robotY + robotX + robotTheta) / denominator,
            fr = (robotY - robotX - robotTheta) / denominator,
            bl = (robotY - robotX + robotTheta) / denominator,
            br = (robotY + robotX - robotTheta) / denominator
        )
    }

    fun runRobotPowersCmd(robotX: Double, robotY: Double, robotTheta: Double) = run {
        runRobotPowers(robotX, robotY, robotTheta)
    }.setIsDone { false }

    fun joystickDrive(
        fieldX: Range,
        fieldY: Range,
        theta: Range,
        smoothingPower: Int
    ) = run {
        val adjustedX = fieldX().pow(smoothingPower).absoluteValue * fieldX().sign
        val adjustedY = fieldY().pow(smoothingPower).absoluteValue * fieldY().sign

        runFieldPowers(adjustedX, adjustedY, theta())
    }
    val zeroIMU = runOnce { io.zeroIMU() }

    val stop = runFieldPowersCmd(0.0, 0.0, 0.0)

    fun runForTime(x: Double, y: Double, t: Double, dur: Duration) =
        runFieldPowersCmd(x, y, t).endAfter(dur).then(stop).requires(this)

    override val defaultCommand = with(Gamepads.gamepad1) {
        joystickDrive(
            leftStickY,
            leftStickX,
            -rightStickX,
            smoothingPower = 2
        )
    }

    private operator fun Range.invoke() = get()
}