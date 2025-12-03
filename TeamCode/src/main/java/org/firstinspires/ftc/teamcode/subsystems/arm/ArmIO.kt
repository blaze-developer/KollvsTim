package org.firstinspires.ftc.teamcode.subsystems.arm

import com.blazedeveloper.chrono.structure.LogTable
import com.blazedeveloper.chrono.structure.LoggableInputs
import dev.nextftc.core.units.Angle

interface ArmIO {
    fun runShoulder(position: Angle)
    fun runElbow(position: Angle)
    fun stop()

    fun updateInputs(inputs: ArmInputs)
}

class ArmInputs : LoggableInputs {
    var shoulderVoltage = 0.0
    var shoulderVelocity = 0.0
    var shoulderAcceleration = 0.0
    var shoulderPosition = 0.0
    var servoDone = false

    override fun toLog(table: LogTable) {
        table.put("ShoulderVolts", shoulderVoltage)
        table.put("ShoulderRadPerSec", shoulderVelocity)
        table.put("ShoulderRadPerSecPerSec", shoulderAcceleration)
        table.put("ShoulderPosition", shoulderPosition)
        table.put("ServoDone", servoDone)
    }

    override fun fromLog(table: LogTable) {
       shoulderVoltage = table.get("ShoulderVolts", shoulderVoltage)
       shoulderVelocity = table.get("ShoulderRadPerSec", shoulderVelocity)
       shoulderAcceleration = table.get("ShoulderRadPerSecPerSec", shoulderAcceleration)
       shoulderPosition = table.get("ShoulderPosition", shoulderPosition)
       servoDone = table.get("ServoDone", servoDone)
    }
}