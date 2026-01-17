package org.firstinspires.ftc.teamcode.subsystems.vision

import com.blazedeveloper.chrono.Logger
import org.firstinspires.ftc.teamcode.subsystems.drive.SubsystemBase

const val ballThreshold = 10.0

class Vision(val io: VisionIO) : SubsystemBase() {
    private val inputs = VisionInputs()

    val colorDetected get() = inputs.color.alpha > 0.65

    override fun periodic() {
        io.updateInputs(inputs)
        Logger.processInputs("Vision", inputs)

        Logger.output("Vision/ColorDetected", colorDetected)
    }

    override fun initialize() = io.init()
}