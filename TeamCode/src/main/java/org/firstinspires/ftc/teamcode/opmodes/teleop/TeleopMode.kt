package org.firstinspires.ftc.teamcode.opmodes.teleop

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import dev.nextftc.ftc.Gamepads
import org.firstinspires.ftc.teamcode.opmodes.RobotOpMode

@TeleOp(name = "LiaTeleop")
class Teleop : RobotOpMode() {
    override fun onStartButtonPressed(): Unit = with(robot) {
        Gamepads.gamepad1.x whenBecomesTrue drive.stop
    }
}