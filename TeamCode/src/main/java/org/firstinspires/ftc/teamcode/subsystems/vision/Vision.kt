package org.firstinspires.ftc.teamcode.subsystems.vision

import com.blazedeveloper.chrono.Logger
import org.firstinspires.ftc.teamcode.subsystems.drive.SubsystemBase

const val ballThreshold = 10.0

class Vision(val io: VisionIO) : SubsystemBase() {
    private val inputs = VisionInputs()

    val currentColor get() = inputs.color
    val hasBall get() = inputs.beamDistanceCm < ballThreshold

    override fun periodic() {
        io.updateInputs(inputs)
        Logger.processInputs("Vision", inputs)

        Logger.output("Vision/HasBall", hasBall)
    }

    override fun initialize() = io.init()
}