package org.firstinspires.ftc.teamcode.logging.dataflow.rlog

import org.firstinspires.ftc.teamcode.logging.dataflow.LogReceiver
import org.firstinspires.ftc.teamcode.logging.structure.LogTable
import java.io.File

class LogWriter(val fileName: String) : LogReceiver {
    override fun start() {
        println("Writing File...")
        val file = File("sdcard/FIRST/java/src/$fileName")
        file.writeText("This is definitely a log file")
        file.setReadable(true)
        println("File wrote!")
    }

    override fun stop() {
        File("sdcard/FIRST/meow.rlog").writeText("This is a meow meow :3")
    }

    override fun receive(table: LogTable) {
//        println("Recieving Log File")
    }
}