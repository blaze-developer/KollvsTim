package org.firstinspires.ftc.teamcode.subsystems

import com.blazedeveloper.chrono.Logger
import dev.nextftc.hardware.impl.ServoEx
import org.firstinspires.ftc.teamcode.subsystems.drive.SubsystemBase
import kotlin.time.Duration.Companion.seconds

const val openPosition = 0.0
const val closePosition = 0.3

class Yoinker(val io: YoinkerIO) : SubsystemBase() {
    val movementDuration = 0.5.seconds

    val open = instant {
        io.setPosition(openPosition)
        Logger.output("Yoinking", false)
    }.thenWait(movementDuration)

    val close = instant {
        io.setPosition(closePosition)
        Logger.output("Yoinking", true)
    }.thenWait(movementDuration)
}

interface YoinkerIO {
    fun setPosition(position: Double) {}
}

class YoinkerIOHardware : YoinkerIO {
    private val yoinker = ServoEx("yoinker")

    override fun setPosition(position: Double) {
        yoinker.position = position
    }
}