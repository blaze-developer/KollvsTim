package com.blazedeveloper.logging.structure

import com.blazedeveloper.logging.structure.LogValue.Companion.asLogValue
import kotlin.time.Duration
import kotlin.time.Duration.Companion.microseconds
import kotlin.time.DurationUnit

class LogTable(
    private val sharedTimestamp: SharedTimestamp,
    private val prefix: String = "/",
    private val mutableData: MutableMap<String, LogValue> = mutableMapOf(),
) {
    /** Object to hold a timestamp that can be shared between subtables. **/
    class SharedTimestamp(var value: Duration)

    /** Creates a LogTable based on a timestamp as the root table. **/
    constructor(timestamp: Duration) : this(SharedTimestamp(timestamp))

    /** Creates a LogTable based on a timestamp in microseconds as the root table. **/
    constructor(timestamp: Long) : this(timestamp.microseconds)

    /** The current timestamp of the LogTable stored as a unitless Duration for easy comparisons **/
    var timestamp: Duration
        get() = sharedTimestamp.value
        set(value) { sharedTimestamp.value = value }

    /** The current timestamp of ths LogTable stored in Seconds **/
    val timestampSeconds: Double get() = timestamp.toDouble(DurationUnit.SECONDS)

    val data: Map<String, LogValue>
        get() = mutableData.toMap()

    /**
     * Gets a subtable of this LogTable with a specified name.
     * Changes to this subtable will be reflected in the parent table.
     */
    fun subtable(name: String) = LogTable(
        this.sharedTimestamp,
        this.prefix + name + "/",
        this.mutableData
    )

    /** Put a raw LogValue into the table. **/
    fun put(key: String, value: LogValue) = mutableData.put(prefix + key, value)

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
    fun get(key: String, default: LogValue) = mutableData[prefix + key] ?: default

    /** Get a String from the table **/
    fun get(key: String, default: String) = get(key, default.asLogValue()).value as? String ?: default

    /** Get a Boolean from the table **/
    fun get(key: String, default: Boolean) = get(key, default.asLogValue()).value as? Boolean ?: default

    /** Get a Int from the table **/
    fun get(key: String, default: Int) = get(key, default.asLogValue()).value as? Int ?: default

    /** Get a Long from the table **/
    fun get(key: String, default: Long) = get(key, default.asLogValue()).value as? Long ?: default

    /** Get a Float from the table **/
    fun get(key: String, default: Float) = get(key, default.asLogValue()).value as? Float ?: default

    /** Get a Double from the table **/
    fun get(key: String, default: Double) = get(key, default.asLogValue()).value as? Double ?: default

    /** Get a ByteArray from the table **/
    fun get(key: String, default: ByteArray) = get(key, default.asLogValue()).value as? ByteArray ?: default

    /** Get a DoubleArray from the table **/
    fun get(key: String, default: DoubleArray) = get(key, default.asLogValue()).value as? DoubleArray ?: default
}