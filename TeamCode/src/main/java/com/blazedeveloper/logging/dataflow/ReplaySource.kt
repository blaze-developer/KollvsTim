package com.blazedeveloper.logging.dataflow

import com.blazedeveloper.logging.structure.LogTable

interface ReplaySource {
    fun start()
    fun stop()
    fun updateTable(table: LogTable): Boolean
    operator fun invoke(table: LogTable) = updateTable(table)
}