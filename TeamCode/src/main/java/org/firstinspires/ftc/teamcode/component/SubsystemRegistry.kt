package org.firstinspires.ftc.teamcode.component

import dev.nextftc.core.commands.Command
import dev.nextftc.core.commands.CommandManager
import dev.nextftc.core.components.Component
import dev.nextftc.core.components.SubsystemComponent
import dev.nextftc.core.subsystems.Subsystem

object SubsystemRegistry : Component {
    private val subsystems = mutableSetOf<Subsystem>()

    override fun preInit() = subsystems.forEach { it.initialize() }

    override fun preUpdate() = updateSubsystems()
    override fun preWaitForStart() = updateSubsystems()

    private fun updateSubsystems() = subsystems.forEach {
        it.periodic()
        if (!CommandManager.hasCommandsUsing(it)) it.defaultCommand.schedule()
    }

    fun register(sys: Subsystem) = subsystems.addAll(sys.flatten())

    private fun Subsystem.flatten(): Collection<Subsystem> =
        listOf(this) + this.subsystems.flatMap { it.flatten() }
}