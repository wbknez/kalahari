package com.willoutwest.kalahari.math

import com.willoutwest.kalahari.util.hash

/**
 * Represents an infinite, directed line in three dimensional Cartesian space.
 *
 * @property dir
 *           The direction to point in.
 * @property invDir
 *           The inverse of the direction to point in.
 * @property origin
 *           The starting point.
 */
class Ray3(@JvmField val dir: Vector3 = Vector3.Unit.clone(),
           @JvmField val origin: Point3 = Point3.Zero.clone()) : Cloneable {

    val invDir = this.dir.invert()

    /**
     * Constructor.
     *
     * @param dX
     *        The x-axis direction to use.
     * @param dY
     *        The y-axis direction to use.
     * @param dZ
     *        The z-axis direction to use.
     * @param oX
     *        The x-axis origin to use.
     * @param oY
     *        The y-axis origin to use.
     * @param oZ
     *        The z-axis origin to use.
     */
    constructor(dX: Float, dY: Float, dZ: Float, oX: Float, oY: Float,
                oZ: Float) : this(Vector3(dX, dY, dZ), Point3(oX, oY, oZ))

    /**
     * Constructor.
     *
     * @param ray
     *        The ray to copy from.
     */
    constructor(ray: Ray3?) : this(ray!!.dir.clone(), ray.origin.clone())

    public override fun clone(): Ray3 = Ray3(this)

    override fun equals(other: Any?): Boolean =
        when(other) {
            is Ray3 -> this.dir == other.dir && this.origin == other.origin
            else    -> false
        }

    override fun hashCode(): Int = hash(this.dir, this.origin)

    /**
     * Normalizes this ray by computing the normalization of the direction
     * (and inverse direction), if appropriate.
     *
     * @return A normalized ray.
     */
    fun normalize(): Ray3 {
        val ray = Ray3(this)

        ray.dir.normalizeSelf()
        ray.invDir.normalizeSelf()

        return ray
    }

    /**
     * Normalizes this ray by computing the normalization of the direction,
     * if appropriate, and modifies this ray as a result.
     *
     * @return A reference to this ray for easy chaining.
     */
    fun normalizeSelf(): Ray3 {
        this.dir.normalizeSelf()
        this.invDir.set(this.dir).invertSelf().normalizeSelf()

        return this
    }

    /**
     * Computes the resultant point from projecting this ray through the
     * specified amount of parametric time.
     *
     * @param t
     *        The parametric time to use.
     * @return A point on a ray at some parametric time.
     */
    fun projectAlong(t: Float): Point3 = this.projectAlong(t, Point3())

    /**
     * Computes the resultant point from projecting this ray through the
     * specified amount of parametric time and stores the result in the
     * specified point.
     *
     * @param t
     *        The parametric time to use.
     * @param store
     *        The point to store the result in.
     * @return A reference to store for easy chaining.
     */
    fun projectAlong(t: Float, store: Point3): Point3 =
        store.set(
            this.origin.x + t * this.dir.x,
            this.origin.y + t * this.dir.y,
            this.origin.z + t * this.dir.z
        )

    /**
     * Sets the origin and direction of this ray using the specified
     * components of each.
     *
     * @param dX
     *        The x-axis direction to use.
     * @param dY
     *        The y-axis direction to use.
     * @param dZ
     *        The z-axis direction to use.
     * @param oX
     *        The x-axis origin to use.
     * @param oY
     *        The y-axis origin to use.
     * @param oZ
     *        The z-axis origin to use.
     * @return A reference to this ray for easy chaining.
     */
    fun set(dX: Float, dY: Float, dZ: Float, oX: Float, oY: Float, oZ: Float):
        Ray3 {
        this.dir.set(dX, dY, dZ)
        this.invDir.set(this.dir).invertSelf()
        this.origin.set(oX, oY, oZ)

        return this
    }

    /**
     * Sets the origin and direction of this ray to those specified.
     *
     * @param dir
     *        The direction to use.
     * @param origin
     *        The origin to use.
     * @return A reference to this ray for easy chaining.
     */
    fun set(dir: Vector3, origin: Point3): Ray3 =
        this.set(dir.x, dir.y, dir.z, origin.x, origin.y, origin.z)

    /**
     * Sets the origin and direction of this ray to those of the specified one.
     *
     * @param ray
     *        The ray to copy from.
     * @return A reference to this ray for easy chaining.
     */
    fun set(ray: Ray3?): Ray3 =
        this.set(ray!!.dir.x, ray.dir.y, ray.dir.z, ray.origin.x,
                 ray.origin.y, ray.origin.z)

    /**
     * Transforms this ray - both the direction and origin - using the
     * specified matrix.
     *
     * @param mat
     *        The transformation matrix to use.
     * @return A transformed ray.
     */
    fun transform(mat: Matrix4): Ray3 =
        Ray3(this.dir.transform(mat), this.origin.transform(mat))

    /**
     * Transforms this ray - both the direction and origin - using the
     * specified matrix.
     *
     * @param mat
     *        The transformation matrix to use.
     * @return A transformed ray.
     */
    fun transformSelf(mat: Matrix4): Ray3 {
        this.dir.transformSelf(mat)
        this.origin.transformSelf(mat)

        return this
    }

    override fun toString(): String = "(${this.dir}, ${this.origin})"
}