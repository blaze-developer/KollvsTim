package org.firstinspires.ftc.teamcode.logging.dataflow.ftcdashboard

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import org.firstinspires.ftc.teamcode.logging.dataflow.LogReceiver
import org.firstinspires.ftc.teamcode.logging.structure.LogTable

class FTCDashboard : LogReceiver {
    override fun receive(table: LogTable) {
        val packet = TelemetryPacket()

        table.data.forEach {
            packet.put(it.key, it.value)
        }

        FtcDashboard.getInstance().sendTelemetryPacket(packet)
    }
}  