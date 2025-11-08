package org.firstinspires.ftc.teamcode.logging.structure

enum class LoggableType(val wpilogType: String) {
    String("string"),
    Boolean("boolean"),
    Integer("int64"),
    Float("float"),
    Double("double"),
    ByteArray("raw"),
    DoubleArray("double[]")
}