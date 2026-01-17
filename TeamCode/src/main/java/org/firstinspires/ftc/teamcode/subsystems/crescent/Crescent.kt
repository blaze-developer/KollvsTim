package org.firstinspires.ftc.teamcode.subsystems.crescent

import com.blazedeveloper.chrono.Logger
import dev.nextftc.core.commands.delays.Delay
import dev.nextftc.core.commands.delays.WaitUntil
import dev.nextftc.core.commands.groups.ParallelRaceGroup
import org.firstinspires.ftc.teamcode.command.groups.MyParallelGroup
import org.firstinspires.ftc.teamcode.command.groups.MyParallelRaceGroup
import org.firstinspires.ftc.teamcode.command.groups.myEndAfter
import org.firstinspires.ftc.teamcode.command.groups.myRaceWith
import org.firstinspires.ftc.teamcode.command.groups.myUntil
import org.firstinspires.ftc.teamcode.subsystems.drive.SubsystemBase
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class Crescent(val io: CrescentIO) : SubsystemBase() {
    private val inputs = CrescentInputs()

    private val intakePower = 0.5
    private val placePower = 1.0
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
            .setStop { io.stop() }

    val intake = runPower(intakePower).myRaceWith(
        WaitUntil(hasBall).thenWait(60.milliseconds)
    )
    val placeUp = runPower(placePower).myEndAfter(placeTime)
    val placeDown = runPower(-placePower).myEndAfter(placeTime)
}