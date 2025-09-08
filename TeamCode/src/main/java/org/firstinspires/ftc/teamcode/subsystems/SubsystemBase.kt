package org.firstinspires.ftc.teamcode.subsystems

import dev.nextftc.core.commands.utility.InstantCommand
import dev.nextftc.core.commands.utility.LambdaCommand
import dev.nextftc.core.subsystems.Subsystem

abstract class SubsystemBase : Subsystem {
    protected fun runOnce(lambda: Runnable) = InstantCommand(lambda).requires(this)

    protected fun run(lambda: Runnable) = LambdaCommand()
        .setUpdate(lambda)
        .requires(this)
}