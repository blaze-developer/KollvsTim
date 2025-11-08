package org.firstinspires.ftc.teamcode

import dev.nextftc.core.commands.Command
import dev.nextftc.core.components.BindingsComponent
import dev.nextftc.core.components.SubsystemComponent
import dev.nextftc.ftc.NextFTCOpMode
import dev.nextftc.ftc.components.BulkReadComponent
import org.firstinspires.ftc.teamcode.logging.Logger
import org.firstinspires.ftc.teamcode.component.SubsystemRegistry
import org.firstinspires.ftc.teamcode.logging.LoggedNextFTCOpMode
import org.firstinspires.ftc.teamcode.logging.dataflow.ftcdashboard.FTCDashboard
import org.firstinspires.ftc.teamcode.subsystems.drive.Drive

abstract class RobotOpMode : LoggedNextFTCOpMode() {
    protected val drive = Drive("fl", "fr", "bl", "br")
    protected val dore = Dore()

    init {
        addComponents(
            SubsystemRegistry,
            BulkReadComponent,
            BindingsComponent
        )

        Logger.addReceiver(FTCDashboard)
    }   
}

abstract class AutoMode : RobotOpMode() {
    abstract val auto: Command
    override fun onStartButtonPressed() = auto()
}