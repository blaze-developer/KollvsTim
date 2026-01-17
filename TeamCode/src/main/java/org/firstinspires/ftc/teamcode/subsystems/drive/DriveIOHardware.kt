package org.firstinspires.ftc.teamcode.subsystems.drive

import dev.nextftc.hardware.impl.Direction
import dev.nextftc.hardware.impl.IMUEx
import dev.nextftc.hardware.impl.MotorEx

class DriveIOHardware(flName: String, frName: String, blName: String, brName: String) : DriveIO {
    private val frontLeft = MotorEx(flName).brakeMode().reversed()
    private val frontRight = MotorEx(frName).brakeMode().reversed()
    private val backLeft = MotorEx(blName).brakeMode()
    private val backRight = MotorEx(brName).brakeMode().reversed()
    private val imu = IMUEx("imu", Direction.LEFT, Direction.BACKWARD).zeroed()

    override fun runPowers(
        fl: Double,
        fr: Double,
        bl: Double,
        br: Double
    ) {
        frontLeft.power = fl
        frontRight.power = fr
        backLeft.power = bl
        backRight.power = br
    }

    override fun stop() = runPowers(0.0, 0.0, 0.0, 0.0)
    override fun zeroIMU() = imu.zero()

    override fun updateInputs(inputs: DriveInputs) {
        inputs.yawRads = imu.get().inRad

        inputs.flPos = frontLeft.currentPosition
        inputs.flVel = frontLeft.velocity
        inputs.flTicks = frontLeft.rawTicks

        inputs.frPos = frontRight.currentPosition
        inputs.frVel = frontRight.velocity
        inputs.frTicks = frontRight.rawTicks

        inputs.blPos = backLeft.currentPosition
        inputs.blVel = backLeft.velocity
        inputs.blTicks = backLeft.rawTicks

        inputs.brPos = backRight.currentPosition
        inputs.brVel = backRight.velocity
        inputs.brTicks = backRight.rawTicks
    }
}