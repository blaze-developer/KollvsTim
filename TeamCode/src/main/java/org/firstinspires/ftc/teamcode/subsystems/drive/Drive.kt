package org.firstinspires.ftc.teamcode.subsystems.drive

import com.pedropathing.follower.FollowerConstants
import com.pedropathing.ftc.FollowerBuilder
import com.pedropathing.ftc.drivetrains.MecanumConstants
import com.pedropathing.ftc.localization.Encoder
import com.pedropathing.ftc.localization.constants.DriveEncoderConstants
import com.pedropathing.geometry.Pose
import dev.nextftc.bindings.Range
import dev.nextftc.ftc.ActiveOpMode
import dev.nextftc.ftc.Gamepads
import dev.nextftc.hardware.impl.Direction
import dev.nextftc.hardware.impl.IMUEx
import dev.nextftc.hardware.impl.MotorEx
import org.firstinspires.ftc.teamcode.logging.Logger
import org.firstinspires.ftc.teamcode.subsystems.SubsystemBase
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sign
import kotlin.math.sin
import kotlin.time.Duration

class Drive(flName: String, frName: String, blName: String, brName: String) : SubsystemBase() {
    private val frontLeft = MotorEx(flName).brakeMode()
    private val frontRight = MotorEx(frName).brakeMode().reversed()
    private val backLeft = MotorEx(blName).brakeMode()
    private val backRight = MotorEx(brName).brakeMode().reversed()
    private val imu = IMUEx("imu", Direction.UP, Direction.RIGHT).zeroed()

    private val encoderConstants by lazy {
        DriveEncoderConstants()
            .leftFrontMotorName(flName)
            .rightFrontMotorName(frName)
            .leftRearMotorName(blName)
            .rightRearMotorName(brName)
            .leftFrontEncoderDirection(Encoder.FORWARD)
            .rightFrontEncoderDirection(Encoder.REVERSE)
            .leftRearEncoderDirection(Encoder.FORWARD)
            .rightRearEncoderDirection(Encoder.REVERSE)
            .forwardTicksToInches(1.0)
            .strafeTicksToInches(1.0)
            .turnTicksToInches(1.0)
            .robotLength(1.0)
            .robotWidth(1.0)
    }

    private val mecanumConstants by lazy {
        MecanumConstants()
            .leftFrontMotorName(flName)
            .rightFrontMotorName(frName)
            .leftRearMotorName(blName)
            .rightRearMotorName(brName)
    }

    private val follower by lazy {
        FollowerBuilder(FollowerConstants(), ActiveOpMode.hardwareMap)
            .driveEncoderLocalizer(encoderConstants)
            .mecanumDrivetrain(mecanumConstants)
            .build()
    }

    var pose: Pose
        get() = follower.pose
        set(value) { follower.pose = value }

    override fun periodic() {
        with(Logger) {
            log("Drive/FlPower", frontLeft.power)
            log("Drive/FrPower", frontRight.power)
            log("Drive/BlPower", backLeft.power)
            log("Drive/BrPower", backRight.power)
            log("Drive/YawRads", imu().inRad)
//            log("Odometry/Robot", pose)
        }
    }

    fun runFieldPowersCmd(fieldX: Double, fieldY: Double, fieldTheta: Double) = runOnce {
        runFieldPowers(fieldX, fieldY, fieldTheta)
    }.setIsDone { false }

    private fun runFieldPowers(fieldX: Double, fieldY: Double, fieldTheta: Double) {
        val heading = imu().inRad
        val robotX = -(fieldX * sin(-heading) + fieldY * cos(-heading))
        val robotY = fieldX * cos(-heading) - fieldY * sin(-heading)

        runRobotPowers(robotX, robotY, fieldTheta)
    }

    private fun runRobotPowers(robotX: Double, robotY: Double, robotTheta: Double) {
        val denominator =
            max(robotX.absoluteValue + robotY.absoluteValue + robotTheta.absoluteValue, 1.0)

        frontLeft.power = (robotY + robotX + robotTheta) / denominator
        frontRight.power = (robotY - robotX - robotTheta) / denominator
        backLeft.power = (robotY - robotX + robotTheta) / denominator
        backRight.power = (robotY + robotX - robotTheta) / denominator
    }

    fun runRobotPowersCmd(robotX: Double, robotY: Double, robotTheta: Double) = run {
        runRobotPowers(robotX, robotY, robotTheta)
    }.setIsDone { false }

    fun joystickDrive(
        fieldX: Range,
        fieldY: Range,
        theta: Range,
        smoothingPower: Int
    ) = run {
        val adjustedX = fieldX().pow(smoothingPower).absoluteValue * fieldX().sign
        val adjustedY = fieldY().pow(smoothingPower).absoluteValue * fieldY().sign

        runFieldPowers(adjustedX, adjustedY, theta())
    }
    val zeroIMU = runOnce { imu.zero() }

    val stop = runFieldPowersCmd(0.0, 0.0, 0.0)

    fun runForTime(x: Double, y: Double, t: Double, dur: Duration) =
        runFieldPowersCmd(x, y, t).endAfter(dur).then(stop).requires(this)

    override val defaultCommand = with(Gamepads.gamepad1) {
        joystickDrive(
            leftStickY,
            leftStickX,
            -rightStickX,
            smoothingPower = 2
        )
    }

    private operator fun Range.invoke() = get()
    private operator fun IMUEx.invoke() = get()
}