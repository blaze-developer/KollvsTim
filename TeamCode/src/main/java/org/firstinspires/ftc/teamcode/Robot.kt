package org.firstinspires.ftc.teamcode

import dev.nextftc.core.subsystems.Subsystem
import org.firstinspires.ftc.teamcode.subsystems.drive.Drive

class Robot() : Subsystem {
    internal val drive = Drive("left", "right")

    override val subsystems = setOf(drive)
}