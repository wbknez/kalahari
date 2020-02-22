package com.willoutwest.kalahari.scene

import com.willoutwest.kalahari.math.Quaternion
import com.willoutwest.kalahari.math.Vector3

/**
 * Represents the spatial attributes of an actor in three-dimensional space.
 *
 * @property rotation
 *           The rotation along each axis.
 * @property scale
 *           The scale factor along each axis.
 * @property translation
 *           The positional offset along each axis.
 */
data class Motion(val rotation: Quaternion = Quaternion.Identity.clone(),
                  val scale: Vector3 = Vector3.Unit.clone(),
                  val translation: Vector3 = Vector3.Zero.clone())
    : Cloneable {

    /**
     * Constructor.
     *
     * @param motion
     *        The motion to copy from.
     */
    constructor(motion: Motion?)
        : this(motion!!.rotation, motion.scale, motion.translation)

    override fun clone(): Motion = Motion(this)
}