package org.firstinspires.ftc.teamcode.logging.dataflow

import org.firstinspires.ftc.teamcode.logging.structure.LogTable


interface ReplaySource {
    fun start()
    fun stop()
    fun updateTable(table: LogTable): Boolean
    operator fun invoke(table: LogTable) = updateTable(table)
}