package org.firstinspires.ftc.teamcode.subsystems.drive

import com.blazedeveloper.logging.structure.LogTable
import com.blazedeveloper.logging.structure.LoggableInputs

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
        table.put("YawRads", yawRads)
        table.put("PitchRads", pitchRads)
        table.put("RollRads", rollRads)
        table.put("FlPos", flPos)
        table.put("FrPos", frPos)
        table.put("BlPos", blPos)
        table.put("BrPos", brPos)
        table.put("FlVel", flVel)
        table.put("FrVel", frVel)
        table.put("BlVel", blVel)
        table.put("BrVel", brVel)
        table.put("FlTicks", flTicks)
        table.put("FrTicks", frTicks)
        table.put("BlTicks", blTicks)
        table.put("BrTicks", brTicks)
    }

    override fun fromLog(table: LogTable) {
        yawRads = table.get("YawRads", yawRads)
        pitchRads = table.get("PitchRads", pitchRads)
        rollRads = table.get("RollRads", rollRads)
        flPos = table.get("FlPos", flPos)
        frPos = table.get("FrPos", frPos)
        blPos = table.get("BlPos", blPos)
        brPos = table.get("BrPos", brPos)
        flVel = table.get("FlVel", flVel)
        frVel = table.get("FrVel", frVel)
        blVel = table.get("BlVel", blVel)
        brVel = table.get("BrVel", brVel)
        flTicks = table.get("FlTicks", flTicks)
        frTicks = table.get("FrTicks", frTicks)
        blTicks = table.get("BlTicks", blTicks)
        brTicks = table.get("BrTicks", brTicks)
    }
}