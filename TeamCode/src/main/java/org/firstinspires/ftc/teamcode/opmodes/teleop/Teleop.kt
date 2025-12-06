package org.firstinspires.ftc.teamcode.opmodes.teleop

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import dev.nextftc.ftc.Gamepads
import org.firstinspires.ftc.teamcode.RobotOpMode
import org.firstinspires.ftc.teamcode.subsystems.arm.ArmPosition

@TeleOp(name = "TeleopKt")
class Teleop : RobotOpMode() {
    override fun onStartButtonPressed(): Unit = with(Gamepads.gamepad1) {
        drive idleWith drive.joystickDrive(
            leftStickY,
            leftStickX,
            -rightStickX,
            smoothingPower = 2
        )

        y and b whenBecomesTrue drive.zeroIMU

        rightTrigger.asButton { it > 0.8 } whenBecomesTrue manipulator.target(ArmPosition.Deployed)
        leftTrigger.asButton { it > 0.8 } whenBecomesTrue manipulator.stow

        leftBumper whenBecomesTrue yoinker.open
        rightBumper whenBecomesTrue yoinker.close
    }
}