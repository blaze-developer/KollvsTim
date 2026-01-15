package org.firstinspires.ftc.teamcode.subsystems.vision

import com.blazedeveloper.chrono.Logger
import org.firstinspires.ftc.teamcode.subsystems.drive.SubsystemBase

const val ballThreshold = 10.0

class Vision(val io: VisionIO) : SubsystemBase() {
    private val inputs = VisionInputs()

    override fun periodic() {
        io.updateInputs(inputs)
        Logger.processInputs("Vision", inputs)
    }

    override fun initialize() = io.init()
}