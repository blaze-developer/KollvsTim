package org.firstinspires.ftc.teamcode.logging.structure

import org.firstinspires.ftc.teamcode.logging.structure.LogValue.Companion.asLogValue

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
    fun put(key: String, value: String) = put(key, value.asLogValue())

    /** Put a Boolean into the table. **/
    fun put(key: String, value: Boolean) = put(key, value.asLogValue())

    /** Put a Int into the table. **/
    fun put(key: String, value: Int) = put(key, value.asLogValue())

    /** Put a Long into the table. **/
    fun put(key: String, value: Long) = put(key, value.asLogValue())

    /** Put a Float into the table. **/
    fun put(key: String, value: Float) = put(key, value.asLogValue())

    /** Put a Double into the table. **/
    fun put(key: String, value: Double) = put(key, value.asLogValue())

    /** Put a ByteArray into the table. **/
    fun put(key: String, value: ByteArray) = put(key, value.asLogValue())

    /** Put a DoubleArray into the table. **/
    fun put(key: String, value: DoubleArray) = put(key, value.asLogValue())

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

    /** Get a ByteArray from the table **/
    fun get(key: String, default: ByteArray) = mutableEntries[key]?.value as? ByteArray ?: default

    /** Get a DoubleArray from the table **/
    fun get(key: String, default: DoubleArray) = mutableEntries[key]?.value as? ByteArray ?: default
}