package org.firstinspires.ftc.teamcode.subsystems.arm

import dev.nextftc.hardware.impl.MotorEx
import dev.nextftc.hardware.impl.ServoEx

class ManipulatorIOHardware : ManipulatorIO {
    private val arm = MotorEx("arm").zeroed().brakeMode()
    private val wrist = ServoEx("wrist")

    override fun updateInputs(inputs: ManipulatorInputs) {
        inputs.armPosition = arm.currentPosition
        inputs.armVelocity = arm.velocity
        inputs.armPower = arm.power
    }

    override fun setArmPower(power: Double) {
        arm.power = power
    }

    override fun setWristPosition(position: Double) {
        wrist.position = position
    }

    override fun stopArm() = setArmPower(0.0)

}
// TODO: Convert methods to accessors? :3