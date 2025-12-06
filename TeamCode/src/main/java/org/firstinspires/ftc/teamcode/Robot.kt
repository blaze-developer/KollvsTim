@file:Suppress("KotlinConstantConditions")

package org.firstinspires.ftc.teamcode

import com.blazedeveloper.chrono.Logger
import com.blazedeveloper.chrono.dataflow.rlog.RLOGServer
import com.blazedeveloper.chrono.dataflow.rlog.RLOGWriter
import dev.nextftc.core.commands.Command
import dev.nextftc.core.components.BindingsComponent
import dev.nextftc.ftc.components.BulkReadComponent
import org.firstinspires.ftc.teamcode.component.SubsystemRegistry
import org.firstinspires.ftc.teamcode.opmodes.LoggedNextFTCOpMode
import org.firstinspires.ftc.teamcode.subsystems.Yoinker
import org.firstinspires.ftc.teamcode.subsystems.YoinkerIO
import org.firstinspires.ftc.teamcode.subsystems.YoinkerIOHardware
import org.firstinspires.ftc.teamcode.subsystems.arm.Manipulator
import org.firstinspires.ftc.teamcode.subsystems.arm.ManipulatorIO
import org.firstinspires.ftc.teamcode.subsystems.arm.ManipulatorIOHardware
import org.firstinspires.ftc.teamcode.subsystems.drive.Drive
import org.firstinspires.ftc.teamcode.subsystems.drive.DriveIO
import org.firstinspires.ftc.teamcode.subsystems.drive.DriveIOHardware

abstract class RobotOpMode : LoggedNextFTCOpMode() {
    private enum class RobotMode { Real, Replay, Sim }

    private val mode = RobotMode.Real

    protected val drive = Drive(
        if (mode == RobotMode.Real) DriveIOHardware("fl", "fr", "bl", "br")
        else object : DriveIO {}
    )

    protected val manipulator = Manipulator(
        if (mode == RobotMode.Real) ManipulatorIOHardware()
        else object : ManipulatorIO {}
    )

    protected val yoinker = Yoinker(
        if (mode == RobotMode.Real) YoinkerIOHardware()
        else object : YoinkerIO {}
    )

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

        Logger.receivers += RLOGServer()
        Logger.receivers += RLOGWriter()
    }
}

abstract class AutoMode : RobotOpMode() {
    abstract val auto: Command
    override fun onStartButtonPressed() = auto()
}