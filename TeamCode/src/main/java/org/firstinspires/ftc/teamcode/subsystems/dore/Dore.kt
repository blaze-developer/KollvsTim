package org.firstinspires.ftc.teamcode.subsystems.dore

import com.blazedeveloper.logging.Logger
import dev.nextftc.core.commands.delays.Delay
import dev.nextftc.core.commands.groups.SequentialGroup
import org.firstinspires.ftc.teamcode.subsystems.SubsystemBase
import kotlin.time.Duration.Companion.seconds

class Dore(private val io: DoreIO) : SubsystemBase() {
    private val inputs = DoreInputs()

    override fun periodic() {
        io.updateInputs(inputs)
        Logger.processInputs("Dore", inputs)
    }

    val open = runOnce { io.open() }
    val close = runOnce { io.close() }

    val place = SequentialGroup(
        open,
        Delay(0.5.seconds),
        close
    ).requires(this)

    override val defaultCommand = close
}