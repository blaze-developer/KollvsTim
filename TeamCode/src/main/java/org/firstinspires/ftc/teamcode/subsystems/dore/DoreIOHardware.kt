package org.firstinspires.ftc.teamcode.subsystems.dore

import com.blazedeveloper.logging.Logger
import dev.nextftc.core.units.Angle
import dev.nextftc.core.units.deg
import dev.nextftc.hardware.impl.ServoEx

class DoreIOHardware : DoreIO {
    private val servo = ServoEx("dore")
    private val servoRangeDeg = 0.0..300.0

    private fun setPosition(angle: Angle) {
        val effectiveAngle = angle.wrapped.inDeg.coerceIn(servoRangeDeg).deg
        val servoSetpoint = effectiveAngle.inDeg / servoRangeDeg.endInclusive
        servo.position = servoSetpoint

        with (Logger) {
            output("Dore/RequestedAngleRad", angle.inRad)
            output("Dore/EffectiveAngleRad", effectiveAngle.inRad)
            output("Dore/ServoSetpoint", servoSetpoint)
        }
    }

    override fun open() = setPosition(-180.deg)
    override fun close() = setPosition(-120.deg)
}