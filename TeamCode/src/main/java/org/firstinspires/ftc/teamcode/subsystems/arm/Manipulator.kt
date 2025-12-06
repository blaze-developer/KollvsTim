package org.firstinspires.ftc.teamcode.subsystems.arm

import com.blazedeveloper.chrono.Logger
import com.pedropathing.control.PIDFCoefficients
import com.pedropathing.control.PIDFController
import org.firstinspires.ftc.teamcode.subsystems.drive.SubsystemBase

/** The positions at which the arm can target consisting of the arm and wrist position. */
enum class ArmPosition(val arm: Double, val wrist: Double) {
    Stowed(0.0, 0.0),
    Deployed(1000.0, 0.5),
    Placing(500.0, -0.5)
}

class Manipulator(private val io: ManipulatorIO) : SubsystemBase() {
    private val inputs = ManipulatorInputs()

    private val pid = PIDFController(
        PIDFCoefficients(
            0.001,
            0.0,
            0.05,
            0.0
        )
    )

    /** The target position of the arm */
    private var setpoint = ArmPosition.Stowed

    /** Set the target position of the arm. */
    fun target(position: ArmPosition) = instant {
        io.setWristPosition(setpoint.wrist)
        pid.targetPosition = position.arm
    }

    val stow = target(ArmPosition.Stowed)

    override fun periodic() {
        io.updateInputs(inputs)
        Logger.processInputs("Arm", inputs)

        pid.updatePosition(inputs.armPosition)
        io.setArmPower(pid.run())

        Logger.output("Arm/SetpointReached", pid.error < 1)
        Logger.output("Arm/Setpoint", setpoint)
        Logger.output("Arm/SetpointTicks", setpoint.arm)
        Logger.output("Arm/ErrorTicks", pid.error)

        Logger.output("Arm/Wrist/Setpoint", setpoint.wrist)
    }
}