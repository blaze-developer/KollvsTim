package org.firstinspires.ftc.teamcode.subsystems.drive

import com.blazedeveloper.chrono.Logger
import dev.nextftc.bindings.Range
import dev.nextftc.core.commands.Command
import dev.nextftc.core.commands.utility.NullCommand
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sign
import kotlin.math.sin

class Drive(private val io: DriveIO) : SubsystemBase() {
    private val inputs = DriveInputs()

    override fun periodic() {
        io.updateInputs(inputs)
        Logger.processInputs("Drive", inputs)

        Logger.output("TimestampUS", Logger.timestamp.inWholeMicroseconds)
        Logger.output("TestLong", 1L)
        Logger.output("TestInteger", 1)
        Logger.output("TestDouble", 1.0)
    }

    fun runFieldPowersCmd(fieldX: Double, fieldY: Double, fieldTheta: Double) = instant {
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

    fun runRobotPowersCmd(robotX: Double, robotY: Double, robotTheta: Double) = instant {
        runRobotPowers(robotX, robotY, robotTheta)
    }

    fun joystickDrive(
        fieldX: Range,
        fieldY: Range,
        theta: Range,
        smoothingPower: Int,
        driveSlow: () -> Boolean = { false },
        slowFactor: Double = 0.25
    ) = run {
        var adjustedX = fieldX().pow(smoothingPower).absoluteValue * fieldX().sign
        var adjustedY = fieldY().pow(smoothingPower).absoluteValue * fieldY().sign

        if (driveSlow()) {
            adjustedX *= slowFactor
            adjustedY *= slowFactor
        }

        runFieldPowers(adjustedX, adjustedY, theta())
    }

    val zeroIMU = instant { io.zeroIMU(); println("IMU Zeroed! :3") }

    private operator fun Range.invoke() = get()

    override var defaultCommand: Command = NullCommand()

    infix fun idleWith(cmd: Command) { defaultCommand = cmd }
}