package org.firstinspires.ftc.teamcode.logging.dataflow.ftcdashboard

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import org.firstinspires.ftc.teamcode.logging.dataflow.LogReceiver
import org.firstinspires.ftc.teamcode.logging.structure.LogTable

object FTCDashboard : LogReceiver {
    override fun receive(table: LogTable) {
        val packet = TelemetryPacket()

        packet.put("Timestamp", table.timestamp)

        table.data.forEach { (key, field) ->
            packet.put(key, field.value)

//            when (field.type) {
//                LoggableType.String -> packet.put(key, field.value as String)
//                LoggableType.Boolean -> packet.put(key, field.value as Boolean)
//                LoggableType.Integer -> packet.put(key, field.value as Long)
//                LoggableType.Float -> packet.put(key, field.value as Float)
//                LoggableType.Double -> packet.put(key, field.value as Double)
//                LoggableType.ByteArray -> TODO()
//                LoggableType.DoubleArray -> TODO()
//            }
        }

        FtcDashboard.getInstance().sendTelemetryPacket(packet)
    }
}  