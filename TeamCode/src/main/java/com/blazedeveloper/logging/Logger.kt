package com.blazedeveloper.logging

import com.blazedeveloper.logging.dataflow.LogReceiver
import com.blazedeveloper.logging.dataflow.ReplaySource
import com.blazedeveloper.logging.inputs.replayFromTable
import com.blazedeveloper.logging.inputs.writeToLog
import com.blazedeveloper.logging.structure.LogTable
import com.blazedeveloper.logging.structure.LoggableInputs
import dev.nextftc.ftc.ActiveOpMode
import kotlin.system.exitProcess
import kotlin.time.Duration
import kotlin.time.Duration.Companion.nanoseconds

object Logger {
    private val table: LogTable = LogTable()
    private val logReceivers = mutableListOf<LogReceiver>()
    private val metadataPairs = mutableListOf<Pair<String, String>>()

    var replaySource: ReplaySource? = null
    val hasReplaySource: Boolean get() = replaySource != null

    fun interface Addable<T> {
        fun add(toAdd: T)
        operator fun plusAssign(toAdd: T) = add(toAdd)
    }

    /**
     * The synchronized timestamp of the current cycle,
     * this should be used for all replayed logic as it is deterministic and replayable.
     **/
    val timestamp: Duration get() = table.timestamp

    /**
     * Object that user can add log receivers to that accept log
     * data from the Logger and use for streaming, logfiles, etc.
     */
    val receivers = Addable<LogReceiver> { logReceivers += it }

    /**
     * Queues log metadata to be put into the table when
     * the Logger is started.
     */
    val metadata = Addable<Pair<String, String>> { metadataPairs += it }

    /** Starts the Logger and its receivers. */
    fun start() {
        val metadataTable = table.subtable(
            if (hasReplaySource) "RealMetadata"
            else "ReplayMetadata"
        )

        metadataPairs.forEach { (key, value) -> metadataTable.put(key, value) }

        logReceivers.forEach { it.start() }
    }

    /** Stops the Logger and its receivers. */
    fun stop() {
        logReceivers.forEach { it.stop() }
    }

    /** Processes an input for this loop, either logging or replaying from the table. **/
    fun processInputs(subtableName: String, inputs: LoggableInputs) {
        if(hasReplaySource) {
            inputs.fromLog(table.subtable(subtableName))
        } else {
            inputs.toLog(table.subtable(subtableName))
        }
    }

    /** Sets up the table for this cycle. Runs before user code. **/
    fun preUser() {
        // Update timestamps and tables from replay
        if (hasReplaySource) {
            val updated = replaySource?.updateTable(table) ?: false
            if (!updated) {
                exitProcess(1)
            }

            // Replay Gamepads
            ActiveOpMode.gamepad1.replayFromTable(table, 1)
            ActiveOpMode.gamepad2.replayFromTable(table, 2)
        } else {
            table.timestamp = System.nanoTime().nanoseconds
        }
    }

    /** Sends data to receivers. Runs after user code. **/
    fun postUser() {
        // Log Gamepad Inputs
        if (!hasReplaySource) {
            ActiveOpMode.gamepad1.writeToLog(table, 1)
            ActiveOpMode.gamepad2.writeToLog(table, 2)
        }

        logReceivers.forEach { it.receive(table) }
    }

    fun log(key: String, value: String) = table.put(key, value)
    fun log(key: String, value: Boolean) = table.put(key, value)
    fun log(key: String, value: Int) = table.put(key, value)
    fun log(key: String, value: Long) = table.put(key, value)
    fun log(key: String, value: Float) = table.put(key, value)
    fun log(key: String, value: Double) = table.put(key, value)
}