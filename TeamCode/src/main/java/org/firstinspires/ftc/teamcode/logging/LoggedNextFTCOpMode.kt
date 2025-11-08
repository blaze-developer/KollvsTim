package org.firstinspires.ftc.teamcode.logging

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import dev.nextftc.core.commands.CommandManager
import dev.nextftc.core.components.Component
import dev.nextftc.ftc.components.Initializer
import org.firstinspires.ftc.teamcode.component.LoggerComponent
import org.firstinspires.ftc.teamcode.logging.structure.LogTable
import org.firstinspires.ftc.teamcode.logging.structure.LoggableInputs

abstract class LoggedNextFTCOpMode : LinearOpMode() {

    private val _components: MutableSet<Component> = mutableSetOf(LoggerComponent, CommandManager)
    val components: Set<Component> by ::_components

    fun addComponents(vararg components: Component) {
        _components.addAll(components)
    }

    override fun getRuntime(): Double {
        return Logger.timestamp.toDouble() / 1_000_000
    }

    private val inputs = object : LoggableInputs {
        var isActive = false
        var inInit = true
        var stopRequested = false

        override fun toLog(table: LogTable) {
            table.put("LoggedOpMode/isActive", isActive)
            table.put("LoggedOpMode/isInit", inInit)
            table.put("LoggedOpMode/stopRequested", stopRequested)
            table.put("DS:enabled", isActive)
        }

        override fun fromLog(table: LogTable) {
            isActive = table.get("LoggedOpMode/isActive", isActive)
            inInit = table.get("LoggedOpMode/isInit", inInit)
            stopRequested = table.get("LoggedOpMode/stopRequested", stopRequested)
        }
    }

    /** Updates and logs LoggedOpMode inputs that are essential for OpMode functionality **/
    private fun updateInputs() {
        if (!Logger.hasReplaySource) {
            inputs.inInit = opModeInInit()
            inputs.isActive = opModeIsActive()
            inputs.stopRequested = isStopRequested
        }
        Logger.processInputs("LoggedOpMode", inputs)
    }

    override fun runOpMode() {
        try {
            Logger.start()
            components.forEach { it.preInit() }
            onInit()
            components.reversed().forEach { it.postInit() }

            // Wait for start
            while (inputs.inInit) {
                components.forEach { it.preWaitForStart() }
                updateInputs()
                onWaitForStart()
                components.reversed().forEach { it.postWaitForStart() }
            }

            // If we pressed stop after init (instead of start) we want to skip the rest of the OpMode
            // and jump straight to the end
            if (!inputs.stopRequested) {
                components.forEach { it.preStartButtonPressed() }
                onStartButtonPressed()
                components.reversed().forEach { it.postStartButtonPressed() }

                while (inputs.isActive) {
                    components.forEach { it.preUpdate() }
                    updateInputs()
                    CommandManager.run()
                    onUpdate()
                    components.reversed().forEach { it.postUpdate() }
                }
            }

            components.forEach { it.preStop() }
            onStop()
            components.forEach { it.postStop() }
            Logger.stop()
        } catch (e: Exception) {
            // Rethrow the exception as a RuntimeException with the original stack trace at the top
            val runtimeException = RuntimeException(e.message)
            runtimeException.stackTrace = e.stackTrace  // Set the original stack trace at the top
            throw runtimeException  // Throw the custom RuntimeException
        }
    }

    /**
     * Used to generate properties upon OpMode initialization.
     *
     * @param block function to be evaluated upon initialization
     */
    fun <V> onInit(block: OpMode.() -> V): Initializer<V> {
        val initializer = Initializer(block)
        addComponents(initializer)
        return initializer
    }

    /**
     * This function runs ONCE when the init button is pressed.
     */
    open fun onInit() {}

    /**
     * This function runs REPEATEDLY during initialization.
     */
    open fun onWaitForStart() {}

    /**
     * This function runs ONCE when the start button is pressed.
     */
    open fun onStartButtonPressed() {}

    /**
     * This function runs REPEATEDLY when the OpMode is running.
     */
    open fun onUpdate() {}

    /**
     * This function runs ONCE when the stop button is pressed.
     */
    open fun onStop() {}
}