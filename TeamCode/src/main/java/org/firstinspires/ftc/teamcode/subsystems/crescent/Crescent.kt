package org.firstinspires.ftc.teamcode.subsystems.crescent

import com.blazedeveloper.chrono.Logger
import dev.nextftc.core.commands.delays.Delay
import dev.nextftc.core.commands.groups.ParallelRaceGroup
import org.firstinspires.ftc.teamcode.subsystems.drive.SubsystemBase
import kotlin.time.Duration.Companion.seconds

class Crescent(val io: CrescentIO) : SubsystemBase() {
    private val inputs = CrescentInputs()

    private val intakePower = 1.0
    private val placePower = 0.5
    private val placeTime = 1.seconds
    private val hasBall = { inputs.distance < 20 }

    override fun initialize() = io.init()

    override fun periodic() {
        io.updateInputs(inputs)
        Logger.processInputs("Crescent", inputs)
    }

    fun runPower(p: Double) =
        instant { io.setPower(p) }
            .setIsDone { false }
            .setInterruptible(true)
            .setStop { io.stop() }

    val intake = runPower(intakePower).until(hasBall)
    val placeUp =  runPower(placePower).endAfter(placeTime)
    val placeDown = ParallelRaceGroup(
        Delay(1.seconds),
        runPower(0.1)
    ).requires(this)
}