package org.firstinspires.ftc.teamcode.subsystems.drive

import dev.nextftc.core.subsystems.Subsystem
import org.firstinspires.ftc.teamcode.component.SubsystemRegistry

abstract class SubsystemBase : Subsystem {
    init {
        SubsystemRegistry.register(this)
    }
}