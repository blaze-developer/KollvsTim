package org.firstinspires.ftc.teamcode.subsystems.crescent

import com.blazedeveloper.chrono.structure.AutoLoggableInputs
import com.blazedeveloper.chrono.structure.LogTable
import com.blazedeveloper.chrono.structure.LoggableInputs

interface CrescentIO {
    fun updateInputs(inputs: CrescentInputs) {}
    fun init() {}
    fun setPower(power: Double) {}
    fun stop() {}
}

class CrescentInputs : LoggableInputs {
    var initialized = false
    var position = 0.0
    var velocity = 0.0
    var distance = 0.0

    override fun fromLog(table: LogTable) {
        // hehe
    }

    override fun toLog(table: LogTable) {
        table.put("Initialized", initialized)
        table.put("Position", position)
        table.put("Velocity", velocity)
        table.put("Distance", distance)
    }
}