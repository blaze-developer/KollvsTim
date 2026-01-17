package org.firstinspires.ftc.teamcode.opmodes.teleop

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import dev.nextftc.core.commands.delays.Delay
import dev.nextftc.core.commands.groups.SequentialGroup
import dev.nextftc.core.commands.instant
import dev.nextftc.ftc.Gamepads
import org.firstinspires.ftc.teamcode.RobotOpMode
import org.firstinspires.ftc.teamcode.command.placeRight
import org.firstinspires.ftc.teamcode.command.placeUp
import kotlin.time.Duration

@TeleOp(name = "TeleopKt")
class Teleop : RobotOpMode() {
    private fun driveFor(time: Duration, x: Double, y: Double) = SequentialGroup(
        drive.runRobotPowersCmd(x, y, 0.0),
        Delay(time),
        drive.runRobotPowersCmd(0.0, 0.0, 0.0),
    )

    private val bindTriggers = with (Gamepads.gamepad1) {
        instant {
            drive idleWith drive.joystickDrive(
                leftStickY,
                leftStickX,
                -rightStickX,
                smoothingPower = 2,
                driveSlow = leftBumper::get
            )

            y and b whenBecomesTrue drive.zeroIMU

            leftTrigger greaterThan 0.5 whenBecomesTrue crescent.intake
            leftBumper whenBecomesTrue crescent.placeDown

            rightTrigger greaterThan 0.5 whenBecomesTrue placeUp(drive, vision, crescent)
            rightBumper whenBecomesTrue placeRight(drive, vision, crescent)
        }
    }

    override fun onStartButtonPressed(): Unit = with(Gamepads.gamepad1) {
        // Start Button Commands
        bindTriggers.schedule()
    }
}