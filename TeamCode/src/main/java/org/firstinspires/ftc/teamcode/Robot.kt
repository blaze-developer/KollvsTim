package org.firstinspires.ftc.teamcode

import dev.nextftc.core.commands.Command
import dev.nextftc.core.components.BindingsComponent
import dev.nextftc.core.components.SubsystemComponent
import dev.nextftc.ftc.NextFTCOpMode
import dev.nextftc.ftc.components.BulkReadComponent
import org.firstinspires.ftc.teamcode.component.Logger
import org.firstinspires.ftc.teamcode.component.SubsystemRegistry
import org.firstinspires.ftc.teamcode.subsystems.Dore
import org.firstinspires.ftc.teamcode.subsystems.Drive

abstract class RobotOpMode : NextFTCOpMode() {
    protected val drive = Drive("fl", "fr", "bl", "br")
    protected val dore = Dore()

    init {
        addComponents(
            Logger,
            SubsystemRegistry,
            BulkReadComponent,
            BindingsComponent
        )
    }
}

abstract class AutoMode : RobotOpMode() {
    abstract val auto: Command
    override fun onStartButtonPressed() = auto()
}