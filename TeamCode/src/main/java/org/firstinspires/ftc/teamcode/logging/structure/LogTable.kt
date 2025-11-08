package org.firstinspires.ftc.teamcode.logging.structure

import org.firstinspires.ftc.teamcode.logging.structure.LogTable.LogValue.Companion.asLogValue

class LogTable(
    var timestamp: Long,
    val prefix: String = "/"
) {
    // The data in the table, this is kept private so we can filter for types that we want.
    private val mutableEntries: MutableMap<String, LogValue> = mutableMapOf()
    val entries: Map<String, LogValue>
        get() = mutableEntries.toMap()

    /** Put a raw LogValue into the table. **/
    fun put(key: String, value: LogValue) = mutableEntries.put(key, value)

    /** Put a String into the table. **/
    fun put(key: String, value: String) = mutableEntries.put(key, value.asLogValue())

    /** Put a Boolean into the table. **/
    fun put(key: String, value: Boolean) = mutableEntries.put(key, value.asLogValue())

    /** Put a Int into the table. **/
    fun put(key: String, value: Int) = mutableEntries.put(key, value.asLogValue())

    /** Put a Long into the table. **/
    fun put(key: String, value: Long) = mutableEntries.put(key, value.asLogValue())

    /** Put a Float into the table. **/
    fun put(key: String, value: Float) = mutableEntries.put(key, value.asLogValue())

    /** Put a Double into the table. **/
    fun put(key: String, value: Double) = mutableEntries.put(key, value.asLogValue())

    /** Put a Byte into the table. **/
    fun put(key: String, value: Byte) = mutableEntries.put(key, value.asLogValue())

    /** Put a ByteArray into the table. **/
    fun put(key: String, value: ByteArray) = mutableEntries.put(key, value.asLogValue())

    /** Get a raw LogValue from the table **/
    fun get(key: String, default: LogValue) = mutableEntries[key] ?: default

    /** Get a String from the table **/
    fun get(key: String, default: String) = mutableEntries[key]?.value as? String ?: default

    /** Get a Boolean from the table **/
    fun get(key: String, default: Boolean) = mutableEntries[key]?.value as? Boolean ?: default

    /** Get a Int from the table **/
    fun get(key: String, default: Int) = mutableEntries[key]?.value as? Int ?: default

    /** Get a Long from the table **/
    fun get(key: String, default: Long) = mutableEntries[key]?.value as? Long ?: default

    /** Get a Float from the table **/
    fun get(key: String, default: Float) = mutableEntries[key]?.value as? Float ?: default

    /** Get a Double from the table **/
    fun get(key: String, default: Double) = mutableEntries[key]?.value as? Double ?: default

    /** Get a Byte from the table **/
    fun get(key: String, default: Byte) = mutableEntries[key]?.value as? Byte ?: default

    /** Get a ByteArray from the table **/
    fun get(key: String, default: ByteArray) = mutableEntries[key]?.value as? ByteArray ?: default

    /** Get a DoubleArray from the table **/
    fun get(key: String, default: DoubleArray) = mutableEntries[key]?.value as? ByteArray ?: default

    class LogValue(val value: Any, val type: LoggableType) {
        companion object {
            fun String.asLogValue() = LogValue(this, LoggableType.String)
            fun Boolean.asLogValue() = LogValue(this, LoggableType.Boolean)
            fun Int.asLogValue() = LogValue(this, LoggableType.Boolean)
            fun Long.asLogValue() = LogValue(this, LoggableType.Boolean)
            fun Float.asLogValue() = LogValue(this, LoggableType.Boolean)
            fun Double.asLogValue() = LogValue(this, LoggableType.Boolean)
            fun Byte.asLogValue() = LogValue(this, LoggableType.Boolean)
            fun ByteArray.asLogValue() = LogValue(this, LoggableType.Boolean)
            fun DoubleArray.asLogValue() = LogValue(this, LoggableType.Boolean)
        }
    }

    enum class LoggableType {
        String,
        Boolean,
        Int,
        Long,
        Float,
        Double,
        Byte,
        ByteArray,
        DoubleArray
    }
}