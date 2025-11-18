package org.firstinspires.ftc.teamcode.opmodes.teleop

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import dev.nextftc.ftc.Gamepads
import org.firstinspires.ftc.teamcode.RobotOpMode

@TeleOp(name = "TeleopKt")
class Teleop : RobotOpMode() {
    override fun onStartButtonPressed(): Unit = with(Gamepads.gamepad1) {
        drive.defaultCommand = drive.joystickDrive(
            leftStickY,
            leftStickX,
            rightStickX,
            smoothingPower = 2
        )

        y and b whenBecomesTrue drive.zeroIMU
    }
}