package org.firstinspires.ftc.teamcode.subsystems.drive

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles
import org.firstinspires.ftc.teamcode.logging.structure.LogTable
import org.firstinspires.ftc.teamcode.logging.structure.LoggableInputs

interface DriveIO {
    fun runPowers(fl: Double, fr: Double, bl: Double, br: Double) {}
    fun stop() {}
    fun zeroIMU() {}

    fun updateInputs(inputs: DriveInputs) {}
}

class DriveInputs : LoggableInputs {
    var yawRads = 0.0
    var pitchRads = 0.0
    var rollRads = 0.0

    override fun toLog(table: LogTable) {
        table.put("Drive/YawRads", yawRads)
        table.put("Drive/PitchRads", pitchRads)
        table.put("Drive/RollRads", rollRads)
    }

    override fun fromLog(table: LogTable) {
        yawRads = table.get("Drive/YawRads", yawRads)
        pitchRads = table.get("Drive/PitchRads", pitchRads)
        rollRads = table.get("Drive/RollRads", rollRads)
    }
}