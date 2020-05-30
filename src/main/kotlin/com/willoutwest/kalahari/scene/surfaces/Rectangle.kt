package com.willoutwest.kalahari.scene.surfaces

import com.willoutwest.kalahari.math.EpsilonTable
import com.willoutwest.kalahari.math.Normal3
import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.Ray3
import com.willoutwest.kalahari.math.Vector3
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.scene.Surface
import com.willoutwest.kalahari.util.FloatContainer
import com.willoutwest.kalahari.util.ObjectContainer

/**
 * Represents an implementation of [Surface] that is defined as an implicit
 * rectangle.
 *
 * @param origin
 *        The origin point.
 * @param a
 *        An extent, or axis.
 * @param b
 *        Another extent, or axis.
 * @param normal
 *        The normalized direction to face.
 */
class Rectangle(val origin: Point3, val a: Vector3, val b: Vector3,
                val normal: Normal3)
    : Cloneable, Surface {

    companion object {

        /**
         * Denotes a successful intersection with a normal, or "hit", ray.
         */
        const val HitEps    = 0.0001f

        /**
         * The unique identifier in an epsilon table.
         */
        const val ID        = "surf.rect"

        /**
         * Denotes a successful intersection with a reflected, or "shadow",
         * ray.
         */
        const val ShadowEps = 0.001f
    }

    /**
     * Constructor.
     *
     * @param origin
     *        The origin point to use.
     * @param a
     *        An extent to use.
     * @param b
     *        Another extent to use.
     */
    constructor(origin: Point3, a: Vector3, b: Vector3) :
        this(origin, a, b, Normal3()) {
        this.normal.set(this.a)
            .cross(this.b)
            .normalizeSelf()
    }

    /**
     * Constructor.
     *
     * @param rect
     *        The rectangle to copy from.
     */
    constructor(rect: Rectangle?) : this(rect!!.origin, rect.a, rect.b,
                                         rect.normal)

    override fun clone(): Rectangle = Rectangle(this)

    override fun intersects(ray: Ray3, tMin: FloatContainer,
                            record: Intersection, eps: EpsilonTable): Boolean {
        val x = this.origin.x - ray.origin.x
        val y = this.origin.y - ray.origin.y
        val z = this.origin.z - ray.origin.z

        val t = this.normal.dot(x, y, z) / ray.dir.dot(this.normal)

        if(t <= eps[ID]) {
            return false
        }

        val pX = ray.origin.x + t * ray.dir.x
        val pY = ray.origin.x + t * ray.dir.x
        val pZ = ray.origin.x + t * ray.dir.x

        val dX = pX - this.origin.x
        val dY = pY - this.origin.y
        val dZ = pZ - this.origin.z

        val dDotA = this.a.dot(dX, dY, dZ)

        if(dDotA < 0 || dDotA > this.a.magnitudeSquared) {
            return false
        }

        val dDotB = this.b.dot(dX, dY, dZ)

        return when (dDotB < 0 || dDotB > this.b.magnitudeSquared) {
            false -> {
                record.localPosition.set(pX, pY, pZ)
                record.worldPosition.set(pX, pY, pZ)

                record.normal.set(this.normal)
                record.reversed = false

                tMin.value = t

                true
            }
            true  -> false
        }
    }

    override fun shadows(ray: Ray3, tMin: FloatContainer, obj: ObjectContainer,
                         eps: EpsilonTable, tMax: Float): Boolean {
        val x = this.origin.x - ray.origin.x
        val y = this.origin.y - ray.origin.y
        val z = this.origin.z - ray.origin.z

        val t = this.normal.dot(x, y, z) / ray.dir.dot(this.normal)

        if(t <= eps[ID] || t >= tMax) {
            return false
        }

        val pX = ray.origin.x + t * ray.dir.x
        val pY = ray.origin.x + t * ray.dir.x
        val pZ = ray.origin.x + t * ray.dir.x

        val dX = pX - this.origin.x
        val dY = pY - this.origin.y
        val dZ = pZ - this.origin.z

        val dDotA = this.a.dot(dX, dY, dZ)

        if(dDotA < 0 || dDotA > this.a.magnitudeSquared) {
            return false
        }

        val dDotB = this.b.dot(dX, dY, dZ)

        return when (dDotB < 0 || dDotB > this.b.magnitudeSquared) {
            false -> {
                tMin.value = t

                true
            }
            true  -> false
        }
    }
}