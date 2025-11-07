package org.firstinspires.ftc.teamcode.component

import dev.nextftc.core.components.Component
import dev.nextftc.ftc.ActiveOpMode
import org.firstinspires.ftc.teamcode.logging.dataflow.LogReceiver
import org.firstinspires.ftc.teamcode.logging.dataflow.ReplaySource
import org.firstinspires.ftc.teamcode.logging.structure.LogTable
import org.firstinspires.ftc.teamcode.logging.structure.LoggableInputs
import kotlin.system.exitProcess

object Logger : Component {
    private var table: LogTable = LogTable(0)
    private var timeBeforeUpdate = 0L

    private val logReceivers: MutableList<LogReceiver> = mutableListOf()
    private var logSource: ReplaySource? = null
    val hasReplaySource: Boolean get() = logSource != null

    val timestamp: Long get() = table.timestamp

    fun addReceiver(receiver: LogReceiver) = logReceivers.add(receiver)

    fun processInputs(key: String, inputs: LoggableInputs) {
        if(hasReplaySource) {
            inputs.fromLog(LogTable(System.nanoTime()))

            ActiveOpMode.opModeIsActive
        } else {
            inputs.toLog(LogTable(System.nanoTime()))
        }
    }
    
    fun output(key: String, value: String) = table.put(key, value)

    /** Sets up the packet for this loop. Runs before user code. **/
    override fun preUpdate() {
        // Update timestamps and tables from replay
        if (hasReplaySource) {
            val updated = logSource?.updateTable(table) ?: false
            if (!updated) {
                exitProcess(1)
            }
        } else {
            table.timestamp = System.nanoTime()
        }

        timeBeforeUpdate = System.nanoTime()
    }

    /** Sends packets and logs data. Runs after user code. **/
    override fun postUpdate() {
        logReceivers.forEach { it.receive(table) }
    }

    override fun preWaitForStart() = preUpdate()
    override fun postWaitForStart() = postUpdate()
}