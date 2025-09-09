package org.firstinspires.ftc.teamcode.opmodes.auto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.opmodes.AutoMode
import kotlin.time.Duration.Companion.seconds

@Autonomous(name = "Drive Forward (3sec)")
class DriveOff : AutoMode() {
    override val command = auto {
        drive.runPower(1.0, 1.0).endAfter(3.seconds)
    }
}