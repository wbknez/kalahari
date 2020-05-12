package com.willoutwest.kalahari.scene.surfaces

import com.willoutwest.kalahari.math.EpsilonTable
import com.willoutwest.kalahari.math.MathUtils
import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.Ray3
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.scene.Surface
import com.willoutwest.kalahari.util.FloatContainer
import com.willoutwest.kalahari.util.ObjectContainer

/**
 * Represents an implementation of [Surface] that is defined as an implicit
 * sphere.
 *
 * @property center
 *           The location of the center in local space.
 * @property radius
 *           The radius.
 */
data class Sphere(val center: Point3, val radius: Float) : Cloneable, Surface {

    companion object {

        /**
         * Denotes a successful intersection with a normal, or "hit", ray.
         */
        const val HitEps  = 0.0001f

        /**
         * The unique identifier in an epsilon table.
         */
        const val ID      = "surf.sphere"
    }

    /**
     * Constructor.
     *
     * @param sphere
     *        The sphere to copy from.
     */
    constructor(sphere: Sphere?) : this(sphere!!.center, sphere.radius)

    override fun clone(): Sphere = Sphere(this)

    override fun intersects(ray: Ray3, tMin: FloatContainer,
                            record: Intersection, eps: EpsilonTable): Boolean {
        val x = ray.origin.x - this.center.x
        val y = ray.origin.y - this.center.y
        val z = ray.origin.z - this.center.z

        val sDotD = x * ray.dir.x + y * ray.dir.y + z * ray.dir.z
        val sDotS = x * x + y * y + z * z

        val a    = ray.dir.dot(ray.dir)
        val b    = 2f * sDotD
        val c    = sDotS - (this.radius * this.radius)
        val disc = b * b - 4f * a * c

        if(disc < 0f) {
            return false
        }

        val denom = 1f / (2f * a)
        val e     = MathUtils.sqrt(disc)
        val hEps  = eps[ID]

        var t = (-b - e) * denom

        if(t <= hEps) {
            t = (-b + e) * denom
        }

        return when(t <= hEps) {
            false -> {
                ray.projectAlong(t, record.localPosition)
                ray.projectAlong(t, record.worldPosition)

                record.normal.set(ray.dir)
                    .timesSelf(t)
                    .plusSelf(x, y, z)
                    .divSelf(this.radius)
                record.reversed = false

                tMin.value = t

                true
            }
            true  -> false
        }
    }

    override fun shadows(ray: Ray3, tMin: FloatContainer, obj: ObjectContainer,
                         eps: EpsilonTable, tMax: Float): Boolean {
        val x = ray.origin.x - this.center.x
        val y = ray.origin.y - this.center.y
        val z = ray.origin.z - this.center.z

        val sDotD = x * ray.dir.x + y * ray.dir.y + z * ray.dir.z
        val sDotS = x * x + y * y + z * z

        val a    = ray.dir.dot(ray.dir)
        val b    = 2f * sDotD
        val c    = sDotS - (this.radius * this.radius)
        val disc = b * b - 4f * a * c

        if(disc < 0f) {
            return false
        }

        val denom = 1f / (2f * a)
        val e     = MathUtils.sqrt(disc)
        val sEps  = eps[ID]

        var t = (-b - e) * denom

        if(t <= sEps) {
            t = (-b + e) * denom
        }

        return when(t > sEps && tMax > t) {
            false -> false
            true  -> {
                tMin.value = t

                true
            }
        }
    }
}