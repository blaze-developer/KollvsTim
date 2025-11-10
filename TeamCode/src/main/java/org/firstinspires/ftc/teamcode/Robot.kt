@file:Suppress("KotlinConstantConditions")

package org.firstinspires.ftc.teamcode

import com.blazedeveloper.logging.LoggedNextFTCOpMode
import com.blazedeveloper.logging.Logger
import com.blazedeveloper.logging.dataflow.ftcdashboard.FTCDashboard
import com.blazedeveloper.logging.dataflow.rlog.RLOGServer
import dev.nextftc.core.commands.Command
import dev.nextftc.core.components.BindingsComponent
import dev.nextftc.ftc.components.BulkReadComponent
import org.firstinspires.ftc.teamcode.component.SubsystemRegistry
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

        Logger.metadata += "Project" to MAVEN_GROUP
        Logger.metadata += "BuildDate" to BUILD_DATE
        Logger.metadata += "GitHash" to GIT_SHA
        Logger.metadata += "GitBranch" to GIT_BRANCH
        Logger.metadata += "GitDate" to GIT_DATE
        Logger.metadata += "GitStatus" to when(DIRTY) {
            0 -> "All Changes Commited"
            1 -> "Uncommited Changes"
            else -> "Unknown"
        }

        Logger.receivers += FTCDashboard
        Logger.receivers += RLOGServer()

        if (mode == RobotMode.Replay) Logger.replaySource = TODO("No replay sources implemented.")

    }
}

abstract class AutoMode : RobotOpMode() {
    abstract val auto: Command
    override fun onStartButtonPressed() = auto()
}