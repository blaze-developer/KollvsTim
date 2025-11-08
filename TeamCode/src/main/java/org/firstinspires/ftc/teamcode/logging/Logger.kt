package org.firstinspires.ftc.teamcode.logging

import dev.nextftc.ftc.ActiveOpMode
import org.firstinspires.ftc.teamcode.logging.dataflow.LogReceiver
import org.firstinspires.ftc.teamcode.logging.dataflow.ReplaySource
import org.firstinspires.ftc.teamcode.logging.inputs.replayFromTable
import org.firstinspires.ftc.teamcode.logging.inputs.writeToLog
import org.firstinspires.ftc.teamcode.logging.structure.LogTable
import org.firstinspires.ftc.teamcode.logging.structure.LoggableInputs
import kotlin.system.exitProcess

object Logger {
    private var table: LogTable = LogTable(0)

    private val logReceivers: MutableList<LogReceiver> = mutableListOf()
    var replaySource: ReplaySource? = null
    val hasReplaySource: Boolean get() = replaySource != null

    val timestamp: Long get() = table.timestamp

    fun addReceiver(receiver: LogReceiver) = logReceivers.add(receiver)

    fun processInputs(key: String, inputs: LoggableInputs) {
        if(hasReplaySource) {
            inputs.fromLog(table)
        } else {
            inputs.toLog(table)
        }
    }

    fun log(key: String, value: String) = table.put(key, value)
    fun log(key: String, value: Boolean) = table.put(key, value)
    fun log(key: String, value: Int) = table.put(key, value)
    fun log(key: String, value: Long) = table.put(key, value)
    fun log(key: String, value: Float) = table.put(key, value)
    fun log(key: String, value: Double) = table.put(key, value)

    fun start() {
        logReceivers.forEach { it.start() }
    }

    fun stop() {
        logReceivers.forEach { it.stop() }
    }

    /** Sets up the packet for this loop. Runs before user code. **/
    fun preUser() {
        // Update timestamps and tables from replay
        if (hasReplaySource) {
            val updated = replaySource?.updateTable(table) ?: false
            if (!updated) {
                exitProcess(1)
            }

            // Replay Gamepads
            ActiveOpMode.gamepad1.replayFromTable(table)
            ActiveOpMode.gamepad2.replayFromTable(table)
        } else {
            table.timestamp = System.nanoTime() / 1000
        }
    }

    /** Sends packets and logs data. Runs after user code. **/
    fun postUser() {
        // Log Gamepad Inputs
        if (!hasReplaySource) {
            ActiveOpMode.gamepad1.writeToLog(table)
            ActiveOpMode.gamepad2.writeToLog(table)
        }

        logReceivers.forEach { it.receive(table) }
    }
}