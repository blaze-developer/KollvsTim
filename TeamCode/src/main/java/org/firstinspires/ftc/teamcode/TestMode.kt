package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor

@TeleOp
class TestMode : LinearOpMode() {
    override fun runOpMode() {
        val motor = hardwareMap.get(DcMotor::class.java, "motor0")
        motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

        telemetry.addData("Status", "Initialized")
        telemetry.addLine("Hello Driver Hub!")
        telemetry.update()

        waitForStart()

        while(opModeIsActive()) {
            val power = -gamepad2.left_stick_y + -gamepad1.left_stick_y

            motor.power = power.toDouble()

            telemetry.addData("Power", power)
            telemetry.addData("Status", "Running!")
            telemetry.update()
        }
    }
}  