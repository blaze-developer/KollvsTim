package org.firstinspires.ftc.teamcode.opmodes

import dev.nextftc.core.commands.Command
import dev.nextftc.core.components.BindingsComponent
import dev.nextftc.core.components.SubsystemComponent
import dev.nextftc.ftc.NextFTCOpMode
import dev.nextftc.ftc.components.BulkReadComponent
import org.firstinspires.ftc.teamcode.Robot

abstract class RobotOpMode : NextFTCOpMode() {
    protected val robot = Robot()

    /** Create a command object with the Robot's scope. Useful for accessing subsystems. **/
    fun command(makeCommand: Robot.() -> Command) = robot.makeCommand()

    init {
        addComponents(
            SubsystemComponent(robot),
            BulkReadComponent,
            BindingsComponent
        )
    }
}

abstract class AutoMode : RobotOpMode() {
    abstract val auto: Command
    override fun onStartButtonPressed() = auto()
}