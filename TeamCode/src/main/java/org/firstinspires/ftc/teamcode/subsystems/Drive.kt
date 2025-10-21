package org.firstinspires.ftc.teamcode.subsystems

import com.pedropathing.follower.Follower
import com.pedropathing.follower.FollowerConstants
import com.pedropathing.ftc.FollowerBuilder
import com.pedropathing.ftc.drivetrains.MecanumConstants
import dev.nextftc.bindings.Range
import dev.nextftc.core.commands.Command
import dev.nextftc.extensions.pedro.FollowPath
import dev.nextftc.ftc.ActiveOpMode
import dev.nextftc.ftc.Gamepads
import dev.nextftc.hardware.impl.Direction
import dev.nextftc.hardware.impl.IMUEx
import dev.nextftc.hardware.impl.MotorEx
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D
import org.firstinspires.ftc.teamcode.component.Logger
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sign
import kotlin.math.sin

class Drive(flName: String, frName: String, blName: String, brName: String) : SubsystemBase() {
    private val frontLeft = MotorEx(flName).brakeMode()
    private val frontRight = MotorEx(frName).brakeMode().reversed()
    private val backLeft = MotorEx(blName).brakeMode()
    private val backRight = MotorEx(brName).brakeMode().reversed()
    private val imu = IMUEx("imu", Direction.UP, Direction.RIGHT).zeroed()

    // Path Following
    private val mecanumConstants = MecanumConstants()
        .maxPower(1.0)
        .leftFrontMotorName(flName)
        .rightFrontMotorName(frName)
        .leftRearMotorName(blName)
        .rightRearMotorName(brName)

    private val ppConstants = FollowerConstants()
        .mass(5.0)

    private val follower = FollowerBuilder(ppConstants, ActiveOpMode.hardwareMap)
        .mecanumDrivetrain(mecanumConstants)
        .build()

    override fun periodic() {
        follower.update()

        with(Logger) {
            log("Drive/FlPower", frontLeft.power)
            log("Drive/FrPower", frontRight.power)
            log("Drive/BlPower", backLeft.power)
            log("Drive/BrPower", backRight.power)
            log("Drive/YawRads", imu().inRad)
            log("Odometry/Robot", Pose2D(
                DistanceUnit.METER,
                0.0,
                0.0,
                AngleUnit.RADIANS,
                imu().inRad
            ))
        }

        // TODO Add Odometry :3
    }

    override val defaultCommand = with(Gamepads.gamepad1) {
        joystickDrive(
            leftStickY,
            leftStickX,
            rightStickX,
            smoothingPower = 2
        )
    }

    fun joystickDrive(
        fieldX: Range,
        fieldY: Range,
        theta: Range,
        smoothingPower: Int
    ) = run {
        val heading = imu().inRad

        val adjustedX = fieldX().pow(smoothingPower).absoluteValue * fieldX().sign
        val adjustedY = fieldY().pow(smoothingPower).absoluteValue * fieldY().sign

        val robotX = -(adjustedX * sin(-heading) + adjustedY * cos(-heading))
        val robotY = adjustedX * cos(-heading) - adjustedY * sin(-heading)
        val robotTheta = -theta()

        val denominator =
            max(robotX.absoluteValue + robotY.absoluteValue + robotTheta.absoluteValue, 1.0)

        frontLeft.power = (robotY + robotX + robotTheta) / denominator
        frontRight.power = (robotY - robotX - robotTheta) / denominator
        backLeft.power = (robotY - robotX + robotTheta) / denominator
        backRight.power = (robotY + robotX - robotTheta) / denominator
    }

    val zeroIMU = runOnce { imu.zero() }

    private operator fun Range.invoke() = get()
    private operator fun IMUEx.invoke() = get()
}