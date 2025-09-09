package org.firstinspires.ftc.teamcode

import dev.nextftc.core.subsystems.Subsystem
import org.firstinspires.ftc.teamcode.subsystems.Drive

class Robot() : Subsystem {
    internal val drive = Drive("left", "right")

    /** The subsystems to register with the SubsystemComponent **/
    override val subsystems = setOf(drive)
}