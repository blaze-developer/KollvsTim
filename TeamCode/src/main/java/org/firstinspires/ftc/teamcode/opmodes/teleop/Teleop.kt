package org.firstinspires.ftc.teamcode.opmodes.teleop

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import dev.nextftc.core.commands.groups.ParallelGroup
import dev.nextftc.core.commands.groups.SequentialGroup
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

        manipulator.enable.schedule()

        // Replace with triggers later
        val collectButton = leftTrigger greaterThan 0.5
        val placeButton = rightTrigger greaterThan 0.5

        collectButton whenBecomesTrue SequentialGroup(
            manipulator.target(ArmPosition.Collecting),
            yoinker.open,
        )

        collectButton whenBecomesFalse SequentialGroup(
            yoinker.close,
            manipulator.stow
        )

        placeButton whenBecomesTrue manipulator.target(ArmPosition.Placing)

        placeButton whenBecomesFalse SequentialGroup(
            yoinker.open,
            manipulator.stow,
            yoinker.close
        )
    }
}