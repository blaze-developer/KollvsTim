package org.firstinspires.ftc.teamcode

import dev.nextftc.core.components.SubsystemComponent
import dev.nextftc.ftc.NextFTCOpMode
import dev.nextftc.ftc.components.BulkReadComponent
import org.firstinspires.ftc.teamcode.subsystems.Drive

class TeleopMode : NextFTCOpMode() {
    private val drive = Drive("motor_left", "motor_right")

    init {
        addComponents(
            SubsystemComponent(drive),
            BulkReadComponent
        )
    }
}