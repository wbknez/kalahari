package com.willoutwest.kalahari.scene.camera

import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.Vector3

/**
 * Represents an orthonormal viewing basis.
 *
 * @property u
 *           The u coordinate axis.
 * @property v
 *           The v coordinate axis.
 * @property w
 *           The w coordinate axis.
 */
data class Uvw(val u: Vector3 = Vector3.X.clone(),
               val v: Vector3 = Vector3.Y.clone(),
               val w: Vector3 = Vector3.Z.clone()) : Cloneable {

    /**
     * Constructor.
     *
     * @param uvw
     *        The basis to copy from.
     */
    constructor(uvw: Uvw?)
        : this(uvw!!.u.clone(), uvw.v.clone(), uvw.w.clone())

    public override fun clone(): Uvw = Uvw(this)

    /**
     * Checks for and removes any singularity that results from the view
     * direction being either parallel or anti-parallel to the up vector.
     *
     * Technically, the singularity occurs whenever two of the three
     * components of both the eye and look-at points are the same.  However,
     * the textbook - and thus this project - is primarily concerned with
     * problems relating to the y-axis, and so this method explicitly
     * hardcodes the boundary cases of looking vertically up or down.
     *
     * @param eye
     *        The eye point to use.
     * @param lookAt
     *        The look-at point to look at.
     * @return A reference to this orthonormal viewing basis for easy chaining.
     */
    fun removeSingularity(eye: Point3, lookAt: Point3): Uvw {
        val x = eye.x.compareTo(lookAt.x)
        val y = eye.y.compareTo(lookAt.y)
        val z = eye.z.compareTo(lookAt.z)

        if (x == 0 && z == 0 && y != 0) {
            if (y > 0) {
                this.u.set(Vector3.Z)
                this.v.set(Vector3.X)
                this.w.set(Vector3.Y)
            }
            else {
                this.u.set(Vector3.X)
                this.v.set(Vector3.Z)
                this.w.set(Vector3.Y)
                    .negateSelf()
            }
        }

        return this
    }

    /**
     * Updates this orthonormal viewing basis based on the specified parameters.
     *
     * @param eye
     *        The eye point to use.
     * @param lookAt
     *        The look-at point to use.
     * @param up
     *        The vector that represents "up" in the coordinate basis to use.
     * @return A reference to this orthonormal viewing basis for easy chaining.
     */
    fun updateBasis(eye: Point3, lookAt: Point3, up: Vector3): Uvw {
        this.w.set(eye)
            .minusSelf(lookAt.x, lookAt.y, lookAt.z)
            .normalizeSelf()
        this.u.set(up)
            .crossSelf(w)
            .normalizeSelf()
        this.v.set(w)
            .crossSelf(u)

        return this.removeSingularity(eye, lookAt)
    }
}