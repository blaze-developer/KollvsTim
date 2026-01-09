package org.firstinspires.ftc.teamcode.subsystems.vision

import com.qualcomm.hardware.rev.Rev2mDistanceSensor
import com.qualcomm.robotcore.hardware.NormalizedColorSensor
import dev.nextftc.ftc.ActiveOpMode
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit

class VisionIORev(val colorSensorName: String, val distanceSensorName: String) : VisionIO {
    private var initialized = false

    private lateinit var colorSensor: NormalizedColorSensor
    private lateinit var rev2m: Rev2mDistanceSensor

    override fun updateInputs(inputs: VisionInputs) {
        if (!initialized) return
        inputs.initialized = true
        inputs.color = colorSensor.normalizedColors
        inputs.beamDistanceCm = rev2m.getDistance(DistanceUnit.CM)
    }

    override fun init() {
        colorSensor = ActiveOpMode.hardwareMap.get(NormalizedColorSensor::class.java, colorSensorName)
        rev2m = ActiveOpMode.hardwareMap.get(Rev2mDistanceSensor::class.java, distanceSensorName)
        initialized = true
    }
}