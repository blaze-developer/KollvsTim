package org.firstinspires.ftc.teamcode.component

import dev.nextftc.core.components.Component
import org.firstinspires.ftc.teamcode.logging.Logger

/** A NextFTC Component to handle and periodically update the logger. **/
object LoggerComponent : Component {
    override fun preUpdate() = Logger.preUser()
    override fun postUpdate() = Logger.postUser()

    override fun preWaitForStart() = preUpdate()
    override fun postWaitForStart() = postUpdate()

    override fun postStop() = Logger.stop()
}