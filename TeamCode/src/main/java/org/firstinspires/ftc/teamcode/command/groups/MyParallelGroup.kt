/*
 * NextFTC: a user-friendly control library for FIRST Tech Challenge
 *     Copyright (C) 2025 Rowan McAlpin
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.firstinspires.ftc.teamcode.command.groups

import dev.nextftc.core.commands.Command
import dev.nextftc.core.commands.groups.CommandGroup

/**
 * A [CommandGroup] that runs all of its children simultaneously.
 */
open class MyParallelGroup(vararg commands: Command) : CommandGroup(*commands) {
    protected val commandDone = children.associateWith { false }.toMutableMap()

    init {
        named("ParallelGroup(${children.joinToString { it.name }})")
    }

    /**
     * This will return false until all of its children are done
     */
    override val isDone: Boolean
        get() {
            val done = commandDone.values.all { it }
            println("GroupDone: $done")
            return done
        }

    init {
        val noConflicts = commands
            .flatMap { it.requirements }
            .groupBy { it }
            .none { it.value.size > 1 }
        check(noConflicts) { "Two or more commands passed to ParallelGroup share one or more requirements" }
    }

    override fun start() {
        commandDone.replaceAll { cmd, done -> false }
        children.forEach {
            it.start()
            println("Command ${it.name} start.")
        }
    }

    override fun update() {
        val iterator = children.iterator()
        while (iterator.hasNext()) {
            val command = iterator.next()
            if (commandDone[command] == true) continue
            command.update()
            println("Command ${command.name} updated.")
            if (!command.isDone) continue
            println("Command ${command.name} finished.")
            command.stop(false)
            commandDone[command] = true
        }
    }

    override fun stop(interrupted: Boolean) {
        children.forEach {
            it.stop(interrupted)
        }

        super.stop(interrupted)
    }
}