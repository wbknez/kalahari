package com.willoutwest.kalahari.scene

import com.willoutwest.kalahari.math.ComputeUtils
import com.willoutwest.kalahari.math.Matrix4
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

    /**
     * Converts this motion into a forward transformation matrix.
     *
     * @return A forward transformation matrix.
     */
    fun toMatrix(): Matrix4 = this.toMatrix(Matrix4())

    /**
     * Converts this motion into a forward transformation matrix and stores
     * the result in the specified matrix.
     *
     * @param store
     *        The matrix to store the result in.
     * @return A reference to [store] for easy chaining.
     */
    fun toMatrix(store: Matrix4): Matrix4 {
        val cache = ComputeUtils.localCache
        val mat   = cache.matrices.borrow()

        this.rotation.toMatrix(mat)
        store.set(Matrix4.Identity)

        store.t03 = this.translation.x
        store.t13 = this.translation.y
        store.t23 = this.translation.z

        store.timesSelf(mat)

        store.t00 *= this.scale.x
        store.t01 *= this.scale.y
        store.t02 *= this.scale.z
        store.t10 *= this.scale.x
        store.t11 *= this.scale.y
        store.t12 *= this.scale.z
        store.t20 *= this.scale.x
        store.t21 *= this.scale.y
        store.t22 *= this.scale.z
        store.t30 *= this.scale.x
        store.t31 *= this.scale.y
        store.t32 *= this.scale.z

        cache.matrices.reuse(mat)

        return store
    }
}