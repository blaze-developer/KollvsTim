package org.firstinspires.ftc.teamcode.subsystems.drive

import dev.nextftc.hardware.impl.Direction
import dev.nextftc.hardware.impl.IMUEx
import dev.nextftc.hardware.impl.MotorEx

class DriveIOHardware(flName: String, frName: String, blName: String, brName: String) : DriveIO {
    private val frontLeft = MotorEx(flName).brakeMode()
    private val frontRight = MotorEx(frName).brakeMode().reversed()
    private val backLeft = MotorEx(blName).brakeMode()
    private val backRight = MotorEx(brName).brakeMode().reversed()
    private val imu = IMUEx("imu", Direction.UP, Direction.RIGHT).zeroed()

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
    }
}