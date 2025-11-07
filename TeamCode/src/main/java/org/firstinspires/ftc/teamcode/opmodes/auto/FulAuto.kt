package org.firstinspires.ftc.teamcode.opmodes.auto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import dev.nextftc.core.commands.groups.SequentialGroup
import dev.nextftc.core.commands.utility.InstantCommand
import org.firstinspires.ftc.teamcode.AutoMode
import org.firstinspires.ftc.teamcode.component.Logger
import kotlin.time.Duration.Companion.seconds

open class FulAuto(val backAnglePwr : Double, val fwdAnglePwr: Double) : AutoMode() {
    private val driveTime = 1.5.seconds
    private val drivePower = 0.5

    override val auto = SequentialGroup(
        InstantCommand { Logger.log("AutoState", "Start") },
        drive.zeroIMU,
        InstantCommand { Logger.log("AutoState", "Driving") },
        drive.runForTime(drivePower, 0.0, fwdAnglePwr, driveTime).endAfter(driveTime + 0.1.seconds),
        InstantCommand { Logger.log("AutoState", "Placing") },
        dore.place,
        InstantCommand { Logger.log("AutoState", "Going Back") },
        drive.runForTime(-drivePower, 0.0, backAnglePwr, driveTime).endAfter(driveTime + 0.1.seconds),
    ).setRequirements(drive, dore)
}

@Autonomous(name = "Place Left")
class FulAutoLeft : FulAuto(backAnglePwr = -0.24, fwdAnglePwr = 0.04)

@Autonomous(name = "Place Right")
class FulAutoRight : FulAuto(backAnglePwr = 0.24, fwdAnglePwr = -0.04)