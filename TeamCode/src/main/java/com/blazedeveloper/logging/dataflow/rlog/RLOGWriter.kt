package com.blazedeveloper.logging.dataflow.rlog

import com.blazedeveloper.logging.dataflow.LogReceiver
import com.blazedeveloper.logging.structure.LogTable
import org.firstinspires.ftc.robotcore.internal.system.AppUtil
import java.io.FileOutputStream

class RLOGWriter(private val fileName: String) : LogReceiver {
    private val encoder = RLOGEncoder()
    private val logFolder = AppUtil.ROOT_FOLDER.resolve("logs")
    private var outputStream: FileOutputStream? = null

    override fun start() {
        if (!logFolder.exists()) logFolder.mkdirs()

        val file = logFolder.resolve(fileName)
        file.delete()
        file.createNewFile()
        println("[RLOGWriter] Saving log to ${file.absolutePath}")

        outputStream = file.outputStream()
    }

    override fun receive(table: LogTable) {
        encoder.encodeTable(table, true)

        if (outputStream == null) error("problem")

        outputStream?.write(encoder.output.array())
    }

    override fun stop() {
        outputStream?.close()
        outputStream = null
    }
}