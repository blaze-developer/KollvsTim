package org.firstinspires.ftc.teamcode.subsystems.crescent

import com.blazedeveloper.chrono.structure.AutoLoggableInputs

interface CrescentIO {
    fun updateInputs(inputs: CrescentInputs) {}
    fun init() {}
    fun setPower(power: Double) {}
    fun stop() {}
}

class CrescentInputs : AutoLoggableInputs() {
    var initialized by logged("Initialized", false)
    var position by logged("Position", 0.0)
    var velocity by logged("VelRadPerSec", 0.0)
    var hasBall by logged("HasBall", false)
}