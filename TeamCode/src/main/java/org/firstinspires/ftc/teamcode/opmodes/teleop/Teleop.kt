package org.firstinspires.ftc.teamcode.opmodes.teleop

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import dev.nextftc.ftc.Gamepads
import org.firstinspires.ftc.teamcode.RobotOpMode

@TeleOp(name = "TeleopKt")
class Teleop : RobotOpMode() {
    override fun onStartButtonPressed(): Unit = with(Gamepads.gamepad1) {
        y and b whenBecomesTrue drive.zeroIMU
        rightTrigger.asButton { it > 0.5 } whenBecomesTrue dore.place
    }
}