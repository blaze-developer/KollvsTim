package org.firstinspires.ftc.teamcode.subsystems.drive

interface DriveIO {
    fun updateInputs(inputs: DriveInputs)
    fun runLeft(power: Double)
    fun runRight(power: Double)
    fun stop()
}

class DriveInputs {
    var leftVelocity = 0.0
    var leftPower = 0.0
    var rightVelocity = 0.0
    var rightPower = 0.0
}