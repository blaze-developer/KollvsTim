package org.firstinspires.ftc.teamcode.logging

class LogTable(
    val timestamp: Long,
    val prefix: String = "/"
) {
    // The data in the table, this is kept private so we can filter for types that we want.
    private val entries: MutableMap<String, Object> = mutableMapOf()
    val data: Map<String, Object>
        get() = entries.toMap()

    fun put(key: String, value: Object) = entries.put(key, value)
    operator fun get(key: String): Object? = entries[key]
}