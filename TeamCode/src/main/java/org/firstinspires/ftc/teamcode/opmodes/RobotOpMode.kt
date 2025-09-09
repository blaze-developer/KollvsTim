package org.firstinspires.ftc.teamcode.opmodes

import dev.nextftc.core.commands.Command
import dev.nextftc.core.components.BindingsComponent
import dev.nextftc.core.components.SubsystemComponent
import dev.nextftc.ftc.NextFTCOpMode
import dev.nextftc.ftc.components.BulkReadComponent
import org.firstinspires.ftc.teamcode.Robot

abstract class RobotOpMode : NextFTCOpMode() {
    protected val robot = Robot()

    init {
        addComponents(
            SubsystemComponent(robot),
            BulkReadComponent,
            BindingsComponent
        )
    }
}

abstract class AutoMode : RobotOpMode() {
    protected fun auto(createAuto: Robot.() -> Command) = createAuto(robot)

    abstract val command: Command

    override fun onStartButtonPressed() = command.schedule()
}