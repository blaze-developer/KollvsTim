package org.firstinspires.ftc.teamcode.subsystems.crescent

import com.qualcomm.hardware.rev.Rev2mDistanceSensor
import dev.nextftc.core.units.cm
import dev.nextftc.core.units.m
import dev.nextftc.ftc.ActiveOpMode
import dev.nextftc.hardware.impl.MotorEx
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit

class CrescentIORev : CrescentIO {
    private val motor = MotorEx("spinny").brakeMode()
    private lateinit var beam: Rev2mDistanceSensor

    private val ballThreshold = 10.cm

    private var initialized = false

    override fun setPower(power: Double) {
        motor.power = power
    }

    override fun stop() = setPower(0.0)

    override fun updateInputs(inputs: CrescentInputs) {
        if (!initialized) return
        inputs.initialized = initialized
        inputs.position = motor.currentPosition
        inputs.velocity = motor.velocity
        inputs.hasBall = beam.getDistance(DistanceUnit.METER).m < ballThreshold
    }

    override fun init() {
        beam = ActiveOpMode.hardwareMap.get(Rev2mDistanceSensor::class.java, "beam")
        initialized = true
    }
}