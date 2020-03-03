package com.willoutwest.kalahari.scene

import com.willoutwest.kalahari.math.ComputeUtils
import com.willoutwest.kalahari.math.Matrix4
import com.willoutwest.kalahari.math.Quaternion
import com.willoutwest.kalahari.math.Vector3
import com.willoutwest.kalahari.math.intersect.BoundingVolume

/**
 * An implementation of [Actor] that provides basic infrastructure for
 * deriving classes.
 */
abstract class AbstractActor(override val name: String) : Actor {

    override var bounds: BoundingVolume? = null

    override var enabled: Boolean = true

    override val invTransform: Matrix4 = Matrix4.Identity.clone()

    override val motion: Motion = Motion()

    override var parent: Actor? = null

    override fun move(x: Float, y: Float, z: Float): Actor {
        this.motion.translation.x += x
        this.motion.translation.y += y
        this.motion.translation.z += z

        return this
    }

    override fun move(vec: Vector3): Actor {
        this.motion.translation.plusSelf(vec)

        return this
    }

    override fun rotate(angle: Float, axis: Vector3): Actor {
        val cache = ComputeUtils.localCache
        val quat  = cache.quats.borrow()

        quat.setFromAxis(angle, axis)
        this.motion.rotation.timesSelf(quat)

        cache.quats.reuse(quat)

        return this
    }

    override fun rotate(roll: Float, pitch: Float, yaw: Float): Actor {
        val cache = ComputeUtils.localCache
        val quat  = cache.quats.borrow()

        quat.setFromEuler(roll, pitch, yaw)
        this.motion.rotation.timesSelf(quat)

        cache.quats.reuse(quat)

        return this
    }

    override fun rotate(quat: Quaternion): Actor {
        this.motion.rotation.timesSelf(quat)

        return this
    }

    override fun scale(x: Float, y: Float, z: Float): Actor {
        this.motion.scale.x *= x
        this.motion.scale.y *= y
        this.motion.scale.z *= z

        return this
    }

    override fun scale(vec: Vector3): Actor {
        this.motion.scale.x *= vec.x
        this.motion.scale.y *= vec.y
        this.motion.scale.z *= vec.z

        return this
    }

    override fun visit(visitor: (Actor) -> Unit) {
        visitor(this)
    }
}