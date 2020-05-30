package com.willoutwest.kalahari.scene.surfaces

import com.willoutwest.kalahari.math.EpsilonTable
import com.willoutwest.kalahari.math.Normal3
import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.Ray3
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.scene.Surface
import com.willoutwest.kalahari.util.FloatContainer
import com.willoutwest.kalahari.util.ObjectContainer

/**
 * Represents an implementation of [Surface] that is defined as an implicit
 * plane.
 *
 * @param point
 *        A point on a plane.
 * @param normal
 *        The normalized direction to face.
 */
class Plane(val point: Point3, val normal: Normal3): Cloneable, Surface {

    companion object {

        /**
         * Denotes a successful intersection with a normal, or "hit", ray.
         */
        const val HitEps    = 0.0001f

        /**
         * The unique identifier in an epsilon table.
         */
        const val ID        = "surf.plane"

        /**
         * Denotes a successful intersection with a reflected, or "shadow",
         * ray.
         */
        const val ShadowEps = 0.001f
    }

    /**
     * Constructor.
     *
     * @param plane
     *        The plane to copy from.
     */
    constructor(plane: Plane?) : this(plane!!.point, plane.normal)

    override fun clone(): Plane = Plane(this)

    override fun intersects(ray: Ray3, tMin: FloatContainer,
                            record: Intersection, eps: EpsilonTable): Boolean {
        val t    = ray.intersectsAt(this.point, this.normal)

        return when(t <= eps[ID]) {
            false -> {
                ray.projectAlong(t, record.localPosition)
                ray.projectAlong(t, record.worldPosition)

                record.normal.set(this.normal)
                record.reversed = false

                tMin.value      = t

                true
            }
            true  -> false
        }
    }

    override fun shadows(ray: Ray3, tMin: FloatContainer, obj: ObjectContainer,
                         eps: EpsilonTable, tMax: Float): Boolean {
        val t    = ray.intersectsAt(this.point, this.normal)

        return when(t > eps[ID] && tMax > t) {
            false -> false
            true  -> {
                tMin.value = t

                true
            }
        }
    }
}