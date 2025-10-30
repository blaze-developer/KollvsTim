package org.firstinspires.ftc.teamcode.subsystems

import dev.nextftc.core.commands.utility.InstantCommand
import dev.nextftc.core.commands.utility.LambdaCommand
import dev.nextftc.core.subsystems.Subsystem
import org.firstinspires.ftc.teamcode.component.SubsystemRegistry

abstract class SubsystemBase : Subsystem {

    init {
        // Ensure the periodic and initialize method are called, and default commands are ran.
        SubsystemRegistry.register(this)
    }

    protected fun runOnce(lambda: () -> Unit) = InstantCommand(lambda).requires(this)

    protected fun run(lambda: () -> Unit) = LambdaCommand()
        .setUpdate(lambda)
        .requires(this)
}