package org.firstinspires.ftc.teamcode.logging.dataflow.ftcdashboard

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import org.firstinspires.ftc.teamcode.logging.dataflow.LogReceiver
import org.firstinspires.ftc.teamcode.logging.structure.LogTable
import org.firstinspires.ftc.teamcode.logging.structure.LogTable.LoggableType

object FTCDashboard : LogReceiver {
    override fun receive(table: LogTable) {
        val packet = TelemetryPacket()

        table.entries.forEach { (key, field) ->
            packet.put(key, field.value)
        }

        FtcDashboard.getInstance().sendTelemetryPacket(packet)
    }
}  