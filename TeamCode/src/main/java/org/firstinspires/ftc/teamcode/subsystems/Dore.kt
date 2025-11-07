package org.firstinspires.ftc.teamcode.subsystems

import dev.nextftc.core.commands.Command
import dev.nextftc.core.commands.delays.Delay
import dev.nextftc.core.commands.groups.SequentialGroup
import dev.nextftc.core.units.Angle
import dev.nextftc.core.units.deg
import dev.nextftc.hardware.impl.ServoEx
import org.firstinspires.ftc.teamcode.component.Logger
import kotlin.time.Duration.Companion.seconds

class Dore : SubsystemBase() {
    private val servo = ServoEx("dore")
    private val servoRangeDeg = 0.0..300.0

    private fun setPosition(angle: Angle) = runOnce {
        val effectiveAngle = angle.wrapped.inDeg.coerceIn(servoRangeDeg).deg
        val servoSetpoint = effectiveAngle.inDeg / servoRangeDeg.endInclusive
        servo.position = servoSetpoint

        with (Logger) {
            log("Dore/RequestedAngle", angle)
            log("Dore/EffectiveAngle", effectiveAngle)
            log("Dore/ServoSetpoint", servoSetpoint)
        }
    }
    
    val open = setPosition(-180.deg)
    val close = setPosition(-120.deg)

    val place = SequentialGroup(
        open,
        Delay(1.0.seconds),
        close
    ).requires(this)

    override val defaultCommand = close
}