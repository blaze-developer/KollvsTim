package org.firstinspires.ftc.teamcode.subsystems.arm

import com.blazedeveloper.chrono.Logger
import com.pedropathing.control.PIDFCoefficients
import com.pedropathing.control.PIDFController
import org.firstinspires.ftc.teamcode.subsystems.drive.SubsystemBase

/** The positions at which the arm can target consisting of the arm and wrist position. */
enum class ArmPosition(val arm: Double, val wrist: Double) {
    Stowed(-3280.0, 0.0),
    Collecting(-225.0, 0.8),
    Placing(-840.0, 0.8)
}

class Manipulator(private val io: ManipulatorIO) : SubsystemBase() {
    private val inputs = ManipulatorInputs()

    private val pid = PIDFController(
        PIDFCoefficients(
            0.01,
            0.0,
            0.0,
            0.0
        )
    )

    /** Whether the manipulator should be enabled and running its pid controller. */
    private var enabled = false

    val enable = instant { enabled = true }
    val disable = instant { enabled = false }

    /** The target position of the arm */
    private var setpoint = ArmPosition.Stowed

    /** Set the target position of the arm. */
    fun target(position: ArmPosition) = instant {
        setpoint = position
    }

    val stow = target(ArmPosition.Stowed)

    override fun periodic() {
        io.updateInputs(inputs)
        Logger.processInputs("Arm", inputs)

        pid.targetPosition = setpoint.arm ?: inputs.armPosition

        Logger.output("Arm/Enabled", enabled)
        Logger.output("Arm/SetpointReached", pid.error < 1)
        Logger.output("Arm/Setpoint", setpoint)
        Logger.output("Arm/SetpointTicks", pid.targetPosition)
        Logger.output("Arm/ErrorTicks", pid.error)

        if (!enabled) {
//            io.stopArm()
            return;
        }

        Logger.output("Arm/WristSetpoint", setpoint.wrist)
        io.setWristPosition(setpoint.wrist)

        pid.updatePosition(inputs.armPosition)
        io.setArmPower(pid.run())
    }
}