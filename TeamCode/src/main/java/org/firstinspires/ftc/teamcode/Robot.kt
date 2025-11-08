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
import org.firstinspires.ftc.teamcode.logging.dataflow.rlog.LogWriter
import org.firstinspires.ftc.teamcode.logging.dataflow.rlog.RLOGServer
import org.firstinspires.ftc.teamcode.subsystems.drive.Drive
import org.firstinspires.ftc.teamcode.subsystems.drive.DriveIO
import org.firstinspires.ftc.teamcode.subsystems.drive.DriveIOHardware

abstract class RobotOpMode : LoggedNextFTCOpMode() {
    protected val drive = Drive(DriveIOHardware("fl", "fr", "bl","br"))

    init {
        addComponents(
            SubsystemRegistry,
            BulkReadComponent,
            BindingsComponent
        )

        Logger.addReceiver(FTCDashboard)
        Logger.addReceiver(RLOGServer(25565))
        Logger.addReceiver(LogWriter("testfile"))

        Logger.start()
    }
}

abstract class AutoMode : RobotOpMode() {
    abstract val auto: Command
    override fun onStartButtonPressed() = auto()
}