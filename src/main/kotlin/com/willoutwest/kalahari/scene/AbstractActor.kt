package com.willoutwest.kalahari.scene

import com.willoutwest.kalahari.math.intersect.BoundingVolume
import com.willoutwest.kalahari.scene.shadow.ShadowMode

/**
 * An implementation of [Actor] that provides basic infrastructure for
 * deriving classes.
 */
abstract class AbstractActor(override val name: String) :
    AbstractMovable(), Actor {

    override var bounds: BoundingVolume? = null

    override var castsShadows: ShadowMode = ShadowMode.Enable

    override var enabled: Boolean = true

    override var parent: Actor? = null

    override var receivesShadows: ShadowMode = ShadowMode.Enable

    override fun visit(visitor: (Actor) -> Unit) {
        visitor(this)
    }
}