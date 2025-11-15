package com.blazedeveloper.logging

import com.blazedeveloper.logging.structure.LogTable
import com.blazedeveloper.logging.structure.LoggableInputs
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

abstract class LoggedLinearOpMode : LinearOpMode() {
    /** Provides a deterministic and replayable replacement for [opModeIsActive] */
    protected val isActive get() = lifecycleInputs.isActive

    /** Provides a deterministic and replayable replacement for [opModeInInit] */
    protected val inInit get() = lifecycleInputs.inInit

    /** Provides a deterministic and replayable replacement for [isStopRequested] */
    protected val isStopRequested get() = lifecycleInputs.stopRequested

    /** Whether or not we are in the first log cycle. */
    private var isFirstCycle = true

    /** Override this method and place your code here. */
    abstract fun runLoggedOpMode()

    /** Handles logger lifecycle methods before calling the user code. */
    final override fun runOpMode() {
        Logger.start()

        Logger.preUser() // Start the very first log cycle, or replay the first table.
        updateLifecycleInputs() // Update initial lifecycleInputs to reflect reality or a replay

        /**
         * Run the user code.
         * The first log cycle is ended is ended upon the first user log cycle.
         */
        runLoggedOpMode()

        Logger.stop()
    }

    /**
     * This NEEDS TO BE USED to wrap robot code iterations, e.g. loops on [isActive] or [inInit].
     * Using this to wrap your cycles automatically runs logger lifecycle methods and
     * ensures that data is logged and replayed properly.
     */
    fun loggedCycle(userCode: () -> Unit) {
        // If this is the very first robot cycle, end the init cycle and log its data.
        if (isFirstCycle) {
            Logger.postUser()
            isFirstCycle = false
        }

        Logger.preUser()
        updateLifecycleInputs()
        userCode()
        Logger.postUser()
    }

    /**
     * Properly updates, and processes deterministic lifecycle inputs like [inInit], and [isActive].
     * Ensure you call this, and Logger lifecycle methods in robot iterations if not using
     * convenience method [loggedCycle] that call this automatically.
     */
    protected fun updateLifecycleInputs() {
        if (!Logger.hasReplaySource) {
            lifecycleInputs.inInit = opModeInInit()
            lifecycleInputs.isActive = opModeIsActive()
            lifecycleInputs.stopRequested = isStopRequested
        }
        Logger.processInputs("LoggedOpMode", lifecycleInputs)
    }

    private val lifecycleInputs = object : LoggableInputs {
        // These will be initialized during the first log cycle.
        var isActive = false
        var inInit = false
        var stopRequested = false

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
}