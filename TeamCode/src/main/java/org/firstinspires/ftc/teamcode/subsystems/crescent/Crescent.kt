package org.firstinspires.ftc.teamcode.subsystems.crescent

import com.blazedeveloper.chrono.Logger
import org.firstinspires.ftc.teamcode.subsystems.drive.SubsystemBase

class Crescent(val io: CrescentIO, val hasBall: () -> Boolean) : SubsystemBase() {
    val inputs = CrescentInputs()

    override fun initialize() = io.init()

    override fun periodic() {
        io.updateInputs(inputs)
        Logger.processInputs("Crescent", inputs)
    }
}