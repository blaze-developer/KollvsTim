package org.firstinspires.ftc.teamcode.subsystems

import dev.nextftc.core.commands.delays.Delay
import dev.nextftc.core.commands.groups.SequentialGroup
import dev.nextftc.core.units.Angle
import dev.nextftc.core.units.deg
import dev.nextftc.hardware.impl.ServoEx
import org.firstinspires.ftc.teamcode.component.Logger
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class Dump : SubsystemBase() {
    private val servo = ServoEx("servo_0")
    private val maxAngle = 300.deg

    private fun runPosition(angle: Angle, time: Duration) = runOnce {
        val rawTarget = angle.inDeg / maxAngle.inDeg
        servo.position = rawTarget

        Logger.log("Dump/Target", angle)
        Logger.log("Dump/RawTarget", rawTarget)
    }.and(Delay(time))

    val dump = SequentialGroup(
        runPosition(90.deg, 0.5.seconds),
        runPosition(0.deg, 0.5.seconds)
    )

    val intake = SequentialGroup(
        TODO("Make intake command")
    )
}