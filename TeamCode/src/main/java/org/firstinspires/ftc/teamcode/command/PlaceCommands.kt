package org.firstinspires.ftc.teamcode.command

import dev.nextftc.core.commands.delays.WaitUntil
import dev.nextftc.core.commands.groups.SequentialGroup
import org.firstinspires.ftc.teamcode.command.groups.myEndAfter
import org.firstinspires.ftc.teamcode.command.groups.myRaceWith
import org.firstinspires.ftc.teamcode.command.groups.myUntil
import org.firstinspires.ftc.teamcode.subsystems.crescent.Crescent
import org.firstinspires.ftc.teamcode.subsystems.drive.Drive
import org.firstinspires.ftc.teamcode.subsystems.vision.Vision
import kotlin.time.Duration.Companion.seconds

const val timeAfterLine = 0.6
const val timeOffset = 0.5

fun placeUp(drive: Drive, vision: Vision, crescent: Crescent) =
    drive.alignCenter(vision).then(crescent.placeUp)

fun placeLeft(drive: Drive, vision: Vision, crescent: Crescent) =
    drive.alignLeft(vision).then(crescent.placeDown)

fun placeRight(drive: Drive, vision: Vision, crescent: Crescent) =
    drive.alignRight(vision).then(crescent.placeDown)

fun Drive.alignCenter(vision: Vision) = SequentialGroup(
    moveMagic(-0.3, 0.0, 0.0).myRaceWith(
        WaitUntil { vision.colorDetected }.thenWait(timeAfterLine.seconds)
    ),
    moveMagic(0.0, 0.3, 0.0).myUntil { vision.colorDetected }
)

fun Drive.alignLeft(vision: Vision) = alignCenter(vision).then(offset(-0.1))
fun Drive.alignRight(vision: Vision) = alignCenter(vision).then(offset(0.1))

private fun Drive.offset(p: Double) = moveMagic(0.0, p, 0.0).myEndAfter(timeOffset.seconds)