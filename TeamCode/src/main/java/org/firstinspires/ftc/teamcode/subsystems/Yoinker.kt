package org.firstinspires.ftc.teamcode.subsystems

import com.blazedeveloper.chrono.Logger
import dev.nextftc.hardware.impl.ServoEx
import org.firstinspires.ftc.teamcode.subsystems.drive.SubsystemBase

const val openPosition = 0.0
const val closePosition = 0.5

class Yoinker(val io: YoinkerIO) : SubsystemBase() {
    val open = instant {
        io.setPosition(openPosition)
        Logger.output("Yoinking", false)
    }

    val close = instant {
        io.setPosition(closePosition)
        Logger.output("Yoinking", true)
    }
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