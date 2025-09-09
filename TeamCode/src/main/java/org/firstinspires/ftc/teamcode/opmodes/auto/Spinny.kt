package org.firstinspires.ftc.teamcode.opmodes.auto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import dev.nextftc.core.commands.groups.SequentialGroup
import org.firstinspires.ftc.teamcode.opmodes.AutoMode
import kotlin.time.Duration.Companion.seconds

@Autonomous(name = "Spinny Auto")
class Spinny : AutoMode() {
    override val auto = command {
        SequentialGroup (
            drive.runPower(1.0, 1.0).endAfter(2.5.seconds),
            drive.runPower(0.5, 1.0).endAfter(2.5.seconds),
            drive.runPower(0.0, 1.0).endAfter(10.seconds),
        )
    }
}