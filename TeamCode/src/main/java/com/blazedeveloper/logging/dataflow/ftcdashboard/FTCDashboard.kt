package com.blazedeveloper.logging.dataflow.ftcdashboard

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.blazedeveloper.logging.dataflow.LogReceiver
import com.blazedeveloper.logging.structure.LogTable

object FTCDashboard : LogReceiver {
    override fun receive(table: LogTable) {
        val packet = TelemetryPacket()

        packet.put("Timestamp", table.timestamp)

        table.map.forEach { (key, field) ->
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