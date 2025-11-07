package org.firstinspires.ftc.teamcode.opmodes.auto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import dev.nextftc.core.commands.groups.SequentialGroup
import org.firstinspires.ftc.teamcode.AutoMode
import kotlin.time.Duration.Companion.seconds

@Autonomous(name = "1 Dump Auto")
class DumpAuto : AutoMode() {
    override val auto = SequentialGroup(
        drive.runRobotPowersCmd(1.0, 0.0, 0.0).endAfter(2.seconds),
        dore.place,
        drive.runRobotPowersCmd(-1.0, 0.0, 0.0).endAfter(2.seconds)
    )
}