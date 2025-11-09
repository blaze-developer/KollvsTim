package com.blazedeveloper.logging

import com.blazedeveloper.logging.structure.LogTable
import com.blazedeveloper.logging.structure.LoggableInputs
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import dev.nextftc.core.commands.CommandManager
import dev.nextftc.core.components.Component
import dev.nextftc.ftc.components.Initializer

abstract class LoggedNextFTCOpMode : LinearOpMode() {

    private val _components: MutableSet<Component> = mutableSetOf(CommandManager)
    val components: Set<Component> by ::_components

    fun addComponents(vararg components: Component) {
        _components.addAll(components)
    }

    private val inputs = object : LoggableInputs {
        var isActive = false
        var inInit = true
        var stopRequested = false

        fun process() {
            if (!Logger.hasReplaySource) {
                inInit = opModeInInit()
                isActive = opModeIsActive()
                stopRequested = isStopRequested
            }
            Logger.processInputs("LoggedOpMode", this)
        }

        override fun toLog(table: LogTable) {
            table.put("isActive", isActive)
            table.put("inInit", inInit)
            table.put("stopRequested", stopRequested)
        }

        override fun fromLog(table: LogTable) {
            isActive = table.get("isActive", isActive)
            inInit = table.get("inInit", inInit)
            stopRequested = table.get("stopRequested", stopRequested)
        }
    }

    override fun runOpMode() {
        try {
            Logger.start()
            components.forEach { it.preInit() }
            onInit()
            components.reversed().forEach { it.postInit() }

            // Wait for start
            while (inputs.inInit) {
                Logger.preUser()
                components.forEach { it.preWaitForStart() }
                inputs.process()
                onWaitForStart()
                components.reversed().forEach { it.postWaitForStart() }
                Logger.postUser()
            }

            // If we pressed stop after init (instead of start) we want to skip the rest of the OpMode
            // and jump straight to the end
            if (!inputs.stopRequested) {
                components.forEach { it.preStartButtonPressed() }
                onStartButtonPressed()
                components.reversed().forEach { it.postStartButtonPressed() }

                while (inputs.isActive) {
                    Logger.preUser()
                    components.forEach { it.preUpdate() }
                    inputs.process()
                    CommandManager.run()
                    onUpdate()
                    components.reversed().forEach { it.postUpdate() }
                    Logger.postUser()
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