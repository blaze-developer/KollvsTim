package org.firstinspires.ftc.teamcode.command

import com.blazedeveloper.chrono.input.LoggedTimer
import dev.nextftc.core.commands.Command
import dev.nextftc.core.units.parseDuration
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

class LoggedDelay(
    private val time: Duration
) : Command() {
    init {
        named("LoggedDelay(${time.toDouble(DurationUnit.SECONDS)}s)")
    }

    /**
     * @param time the desired duration of this command, in seconds
     */
    constructor(time: Double) : this(time.seconds)

    /**
     * @param time the desired duration of this command as a string
     */
    constructor(time: String) : this(parseDuration(time))

    private val timer = LoggedTimer()

    override val isDone: Boolean
        get() = timer.elapsed >= time

    override fun start() {
        timer.reset()
    }
}

fun Command.loggedThenWait(duration: Duration) = then(LoggedDelay(duration))
fun Command.loggedThenWait(duration: Double) = then(LoggedDelay(duration))
fun Command.loggedThenWait(duration: String) = then(LoggedDelay(duration))

fun Command.loggedEndAfter(duration: Duration) = withDeadline(LoggedDelay(duration))
fun Command.loggedEndAfter(duration: Double) = withDeadline(LoggedDelay(duration))
fun Command.loggedEndAfter(duration: String) = withDeadline(LoggedDelay(duration))

fun Command.loggedAfterTime(duration: Duration) = LoggedDelay(duration).then(this)
fun Command.loggedAfterTime(duration: Double) = LoggedDelay(duration).then(this)
fun Command.loggedAfterTime(duration: String) = LoggedDelay(duration).then(this)