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
import org.firstinspires.ftc.teamcode.subsystems.crescent.Crescent
import org.firstinspires.ftc.teamcode.subsystems.crescent.CrescentIO
import org.firstinspires.ftc.teamcode.subsystems.crescent.CrescentIORev
import org.firstinspires.ftc.teamcode.subsystems.drive.Drive
import org.firstinspires.ftc.teamcode.subsystems.drive.DriveIO
import org.firstinspires.ftc.teamcode.subsystems.drive.DriveIOHardware
import org.firstinspires.ftc.teamcode.subsystems.vision.Vision
import org.firstinspires.ftc.teamcode.subsystems.vision.VisionIO
import org.firstinspires.ftc.teamcode.subsystems.vision.VisionIORev

abstract class RobotOpMode : LoggedNextFTCOpMode() {
    private enum class RobotMode { Real, Replay, Sim }

    private val mode = RobotMode.Real

    protected val drive = Drive(
        if (mode == RobotMode.Real) DriveIOHardware("fl", "fr", "bl", "br")
        else object : DriveIO {}
    )

    protected val vision = Vision(
        if (mode == RobotMode.Real)
            VisionIORev(colorSensorName = "color")
        else object : VisionIO {}
    )

    protected val crescent = Crescent(
        if (mode == RobotMode.Real) CrescentIORev()
        else object : CrescentIO {}
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