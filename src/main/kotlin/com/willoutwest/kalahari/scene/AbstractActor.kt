package com.willoutwest.kalahari.scene

import com.willoutwest.kalahari.math.intersect.BoundingVolume

/**
 * An implementation of [Actor] that provides basic infrastructure for
 * deriving classes.
 */
abstract class AbstractActor(override val name: String) : Actor {

    override var bounds: BoundingVolume? = null

    override var enabled: Boolean = true

    override var parent: Actor? = null
}