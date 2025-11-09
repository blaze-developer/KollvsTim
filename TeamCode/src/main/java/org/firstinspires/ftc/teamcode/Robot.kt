package org.firstinspires.ftc.teamcode

import dev.nextftc.core.commands.Command
import dev.nextftc.core.components.BindingsComponent
import dev.nextftc.ftc.components.BulkReadComponent
import org.firstinspires.ftc.teamcode.component.SubsystemRegistry
import org.firstinspires.ftc.teamcode.logging.LoggedNextFTCOpMode
import org.firstinspires.ftc.teamcode.logging.Logger
import org.firstinspires.ftc.teamcode.logging.dataflow.ftcdashboard.FTCDashboard
import org.firstinspires.ftc.teamcode.logging.dataflow.rlog.RLOGServer
import org.firstinspires.ftc.teamcode.subsystems.drive.Drive
import org.firstinspires.ftc.teamcode.subsystems.drive.DriveIO
import org.firstinspires.ftc.teamcode.subsystems.drive.DriveIOHardware

abstract class RobotOpMode : LoggedNextFTCOpMode() {
    private enum class RobotMode { Real, Replay }

    private val mode = RobotMode.Real

    protected val drive = Drive(when(mode) {
        RobotMode.Real -> DriveIOHardware("fl", "fr", "bl", "br")
        RobotMode.Replay -> object : DriveIO {}
    })

    init {
        addComponents(
            SubsystemRegistry,
            BulkReadComponent,
            BindingsComponent
        )

        if (mode == RobotMode.Replay) Logger.replaySource = TODO("No replay sources implemented.")

        Logger += FTCDashboard
        Logger += RLOGServer()

    }
}

abstract class AutoMode : RobotOpMode() {
    abstract val auto: Command
    override fun onStartButtonPressed() = auto()
}