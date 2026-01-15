package org.firstinspires.ftc.teamcode.subsystems.vision

import com.blazedeveloper.chrono.structure.AutoLoggableInputs
import com.qualcomm.robotcore.hardware.NormalizedRGBA

class VisionInputs : AutoLoggableInputs() {
    var color by logged("Color", NormalizedRGBA())
    var initialized by logged("Initialized", false)
}

interface VisionIO {
    fun updateInputs(inputs: VisionInputs) {}
    fun init() {}
}