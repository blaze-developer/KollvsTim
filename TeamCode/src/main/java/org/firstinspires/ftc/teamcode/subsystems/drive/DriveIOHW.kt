package org.firstinspires.ftc.teamcode.subsystems.drive

import dev.nextftc.hardware.impl.MotorEx

class DriveIOHardware(leftName: String, rightName: String) : DriveIO {
    private val leftMotor = MotorEx(leftName)
    private val rightMotor = MotorEx(rightName)

    override fun updateInputs(inputs: DriveInputs) = with(inputs) {
        leftPower = leftMotor.power
        leftVelocity = leftMotor.velocity

        rightPower = rightMotor.power
        rightVelocity = rightMotor.velocity
    }

    override fun runLeft(power: Double) {
        leftMotor.power = power
    }

    override fun runRight(power: Double) {
        rightMotor.power = power
    }

    override fun stop() {
        runLeft(0.0)
        runRight(0.0)
    }
}