package org.firstinspires.ftc.teamcode.opmodes.auto

import com.pedropathing.geometry.BezierCurve
import com.pedropathing.geometry.Pose
import dev.nextftc.core.commands.groups.SequentialGroup
import dev.nextftc.core.commands.utility.InstantCommand
import org.firstinspires.ftc.teamcode.AutoMode

class TestPath : AutoMode() {
    private val path = drive

    private val startPose: Pose = TODO()
    private val scorePose: Pose = TODO()

    override val auto = SequentialGroup(
        InstantCommand { drive.pose = Pose() }     )
}