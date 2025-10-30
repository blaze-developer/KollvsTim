package org.firstinspires.ftc.teamcode.opmodes.teleop

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import dev.nextftc.core.commands.utility.InstantCommand
import dev.nextftc.ftc.Gamepads
import dev.nextftc.hardware.driving.FieldCentric
import dev.nextftc.hardware.driving.MecanumDriverControlled
import dev.nextftc.hardware.impl.Direction
import dev.nextftc.hardware.impl.IMUEx
import dev.nextftc.hardware.impl.MotorEx
import org.firstinspires.ftc.teamcode.RobotOpMode

@TeleOp(name = "TeleopKt")
class Teleop : RobotOpMode() {
    override fun onStartButtonPressed(): Unit = with(Gamepads.gamepad1) {
        y and b whenBecomesTrue drive.zeroIMU
        rightTrigger.asButton { it > 0.5 } whenBecomesTrue dore.place
    }
}