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

    var flPos = 0.0
    var frPos = 0.0
    var blPos = 0.0
    var brPos = 0.0

    var flVel = 0.0
    var frVel = 0.0
    var blVel = 0.0
    var brVel = 0.0

    var flTicks = 0.0
    var frTicks = 0.0
    var blTicks = 0.0
    var brTicks = 0.0

    override fun toLog(table: LogTable) {
        table.put("Drive/YawRads", yawRads)
        table.put("Drive/PitchRads", pitchRads)
        table.put("Drive/RollRads", rollRads)
        table.put("Drive/FlPos", flPos)
        table.put("Drive/FrPos", frPos)
        table.put("Drive/BlPos", blPos)
        table.put("Drive/BrPos", brPos)
        table.put("Drive/FlVel", flVel)
        table.put("Drive/FrVel", frVel)
        table.put("Drive/BlVel", blVel)
        table.put("Drive/BrVel", brVel)
        table.put("Drive/FlTicks", flTicks)
        table.put("Drive/FrTicks", frTicks)
        table.put("Drive/BlTicks", blTicks)
        table.put("Drive/BrTicks", brTicks)
    }

    override fun fromLog(table: LogTable) {
        yawRads = table.get("Drive/YawRads", yawRads)
        pitchRads = table.get("Drive/PitchRads", pitchRads)
        rollRads = table.get("Drive/RollRads", rollRads)
        flPos = table.get("Drive/FlPos", flPos)
        frPos = table.get("Drive/FrPos", frPos)
        blPos = table.get("Drive/BlPos", blPos)
        brPos = table.get("Drive/BrPos", brPos)
        flVel = table.get("Drive/FlVel", flVel)
        frVel = table.get("Drive/FrVel", frVel)
        blVel = table.get("Drive/BlVel", blVel)
        brVel = table.get("Drive/BrVel", brVel)
        flTicks = table.get("Drive/FlTicks", flTicks)
        frTicks = table.get("Drive/FrTicks", frTicks)
        blTicks = table.get("Drive/BlTicks", blTicks)
        brTicks = table.get("Drive/BrTicks", brTicks)
    }
}