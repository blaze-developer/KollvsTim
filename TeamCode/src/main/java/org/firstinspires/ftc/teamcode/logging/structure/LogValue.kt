package org.firstinspires.ftc.teamcode.logging.structure

class LogValue(val value: Any, val type: LoggableType) {
    companion object {
        fun String.asLogValue() = LogValue(this, LoggableType.String)
        fun Boolean.asLogValue() = LogValue(this, LoggableType.Boolean)
        fun Int.asLogValue() = LogValue(this, LoggableType.Integer)
        fun Long.asLogValue() = LogValue(this, LoggableType.Integer)
        fun Float.asLogValue() = LogValue(this, LoggableType.Float)
        fun Double.asLogValue() = LogValue(this, LoggableType.Double)
        fun ByteArray.asLogValue() = LogValue(this, LoggableType.ByteArray)
        fun DoubleArray.asLogValue() = LogValue(this, LoggableType.DoubleArray)
    }
}