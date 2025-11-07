package org.firstinspires.ftc.teamcode.logging.structure

class LogTable(
    var timestamp: Long,
    val prefix: String = "/"
) {
    // The data in the table, this is kept private so we can filter for types that we want.
    private val entries: MutableMap<String, Any> = mutableMapOf()
    val data: Map<String, Any>
        get() = entries.toMap()

    fun put(key: String, value: Any) = entries.put(key, value)

    fun get(key: String, default: Long): Long = (entries[key] as Long?) ?: default
    fun get(key: String, default: Boolean): Boolean = (entries[key] as Boolean?) ?: default
}