package org.firstinspires.ftc.teamcode.opmodes.teleop

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import dev.nextftc.core.components.BindingsComponent
import dev.nextftc.core.components.SubsystemComponent
import dev.nextftc.ftc.Gamepads
import dev.nextftc.ftc.NextFTCOpMode
import dev.nextftc.ftc.components.BulkReadComponent
import org.firstinspires.ftc.teamcode.subsystems.drive.Drive

@TeleOp(name = "LiaTeleop")
class TeleopMode : NextFTCOpMode() {
    private val drive = Drive("left", "right")

    init {
        addComponents(
            SubsystemComponent(drive),
            BulkReadComponent,
            BindingsComponent
        )
        drive.povDrive(
            Gamepads.gamepad1.leftStickY,
            Gamepads.gamepad1.rightStickX,
            easingPower = 2
        ).invoke()
    }
}