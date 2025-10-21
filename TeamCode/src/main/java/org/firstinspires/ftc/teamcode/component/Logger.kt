package org.firstinspires.ftc.teamcode.component

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import dev.nextftc.core.components.Component
import dev.nextftc.core.units.Angle
import dev.nextftc.core.units.Distance
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D

object Logger : Component {
    private var packet: TelemetryPacket? = null
    private var timeBeforeUpdate = 0L

    /** Sets up the packet for this loop. Runs before user code. **/
    override fun preUpdate() {
        packet = TelemetryPacket()
        timeBeforeUpdate = System.nanoTime()
    }

    /** Sends packets and logs data. Runs after user code. **/
    override fun postUpdate() {
        log("Logger/UpdateMS", (System.nanoTime() - timeBeforeUpdate).toDouble() / 1000000)

        FtcDashboard.getInstance().sendTelemetryPacket(packet)
    }

    override fun preWaitForStart() = preUpdate()
    override fun postWaitForStart() = postUpdate()

    fun log(key: String, value: Any) = packet?.put(key, value)

    fun log(key: String, value: Angle) = log("${key}Rads", value.inRad)
    fun log(key: String, value: Distance) = log("${key}Meters", value.inMeters)

    fun log(key: String, x: Distance, y: Distance, yaw: Angle) {
        log(key, Pose2D(DistanceUnit.METER, x.inMeters, y.inMeters, AngleUnit.RADIANS, yaw.inRad))
    }

    fun log(key: String, value: Pose2D) {
        log("$key x", value.getX(DistanceUnit.METER))
        log("$key y", value.getY(DistanceUnit.METER))
        log("$key heading", value.getHeading(AngleUnit.RADIANS))
    }
}