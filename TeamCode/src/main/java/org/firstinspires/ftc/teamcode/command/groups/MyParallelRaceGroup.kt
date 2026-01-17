package org.firstinspires.ftc.teamcode.command.groups

import dev.nextftc.core.commands.Command
import dev.nextftc.core.commands.delays.Delay
import dev.nextftc.core.commands.delays.WaitUntil
import kotlin.time.Duration

class MyParallelRaceGroup(vararg commands: Command) : MyParallelGroup(*commands) {
    init {
        named("ParallelRaceGroup(${children.joinToString { it.name }})")
    }

    /**
     * This will return false until one of its children is done
     */
    override val isDone: Boolean
        get() = commandDone.values.any { it }
}

fun Command.myUntil(predicate: () -> Boolean) = MyParallelRaceGroup(
    WaitUntil(predicate),
    this
)

fun Command.myEndAfter(duration: Duration) = MyParallelRaceGroup(
    Delay(duration),
    this
)

fun Command.myRaceWith(cmd: Command) = MyParallelRaceGroup(
    cmd,
    this
)