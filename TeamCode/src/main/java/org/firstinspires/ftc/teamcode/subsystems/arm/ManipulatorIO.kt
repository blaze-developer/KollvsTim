package org.firstinspires.ftc.teamcode.subsystems.arm

import com.blazedeveloper.chrono.structure.LogTable
import com.blazedeveloper.chrono.structure.LoggableInputs

interface ManipulatorIO {
    fun setArmPower(power: Double) {}
    fun setWristPosition(position: Double) {}
    fun stopArm() {}
    fun updateInputs(inputs: ManipulatorInputs) {}
}

class ManipulatorInputs : LoggableInputs {
    var armPower = 0.0
    var armVelocity = 0.0
    var armPosition = 0.0

    override fun toLog(table: LogTable) {
        table.put("ArmPower", armPower)
        table.put("ArmRadPerSec", armVelocity)
        table.put("ArmPosition", armPosition)
    }

    override fun fromLog(table: LogTable) {
        armPower = table.get("ArmPower", armPower)
        armVelocity = table.get("ArmRadPerSec", armVelocity)
        armPosition = table.get("ArmPosition", armPosition)
    }
}