package com.willoutwest.kalahari.scene.surfaces

import com.willoutwest.kalahari.math.EpsilonTable
import com.willoutwest.kalahari.math.MathUtils
import com.willoutwest.kalahari.math.Ray3
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.scene.Surface
import com.willoutwest.kalahari.util.FloatContainer
import com.willoutwest.kalahari.util.ObjectContainer

/**
 * Represents an implementation of [Surface] that is defined as an implicit
 * open-ended cylinder.
 *
 * @property bottom
 *           The bottom y-axis coordinate.
 * @property radius
 *           The cylinder radius.
 * @property top
 *           The top y-axis coordinate.
 */
data class Cylinder(val bottom: Float, val top: Float, val radius: Float) :
    Cloneable, Surface {

    companion object {

        /**
         * Denotes a successful intersection with a normal, or "hit", ray.
         */
        const val HitEps  = 0.0001f

        /**
         * The unique identifier in an epsilon table.
         */
        const val ID      = "surf.cylinder"
    }

    constructor(cyl: Cylinder?) : this(cyl!!.bottom, cyl.top, cyl.radius)

    override fun clone(): Cylinder = Cylinder(this)

    override fun intersects(ray: Ray3, tMin: FloatContainer,
                            record: Intersection, eps: EpsilonTable): Boolean {
        val a    = ray.dir.dot(ray.dir.x, 0f, ray.dir.z)
        val b    = 2f * a
        val c    = a - (this.radius * this.radius)
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

        val yHit = ray.origin.y + t * ray.dir.y

        return when(t < hEps || yHit <= this.bottom || yHit >= this.top) {
            false -> {
                ray.projectAlong(t, record.localPosition)
                ray.projectAlong(t, record.worldPosition)

                record.normal.set(ray.dir.x, 0f, ray.dir.z)
                    .timesSelf(t)
                    .plusSelf(ray.origin.x, 0f, ray.origin.z)
                    .divSelf(this.radius)
                record.reversed = -ray.dir.dot(record.normal) < 0f

                tMin.value = t

                true
            }
            true  -> false
        }
    }

    override fun shadows(ray: Ray3, tMin: FloatContainer, obj: ObjectContainer,
                         eps: EpsilonTable, tMax: Float): Boolean {
        val a    = ray.dir.dot(ray.dir.x, 0f, ray.dir.z)
        val b    = 2f * a
        val c    = a - (this.radius * this.radius)
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

        val yHit = ray.origin.y + t * ray.dir.y

        return when(t < sEps || tMax <= t || yHit <= this.bottom ||
                    yHit >= this.top) {
            false -> {
                tMin.value = t

                true
            }
            true  -> false
        }
    }
}