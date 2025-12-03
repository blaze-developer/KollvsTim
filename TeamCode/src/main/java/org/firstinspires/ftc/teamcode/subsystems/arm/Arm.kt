package org.firstinspires.ftc.teamcode.subsystems.arm

import com.blazedeveloper.chrono.Logger
import org.firstinspires.ftc.teamcode.subsystems.drive.SubsystemBase

class Arm(private val io: ArmIO) : SubsystemBase() {
    private val inputs = ArmInputs()

    override fun periodic() {
        io.updateInputs(inputs)
        Logger.processInputs("Arm", inputs)

        // Do periodic stuff
    }
}