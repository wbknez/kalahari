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
 * disk.
 *
 * @param center
 *        The location of the center in local space.
 * @param radius
 *        The radius.
 * @param normal
 *        The normalized direction to face.
 */
data class Disk(val center: Point3, val radius: Float, val normal: Normal3)
    : Cloneable, Surface {

    companion object {
        /**
         * Denotes a successful intersection with a normal, or "hit", ray.
         */
        const val HitEps  = 0.0001f

        /**
         * The unique identifier in an epsilon table.
         */
        const val ID      = "surf.disk"
    }

    constructor(disk: Disk?) : this(disk!!.center, disk.radius, disk.normal)

    override fun clone(): Disk = Disk(this)

    override fun intersects(ray: Ray3, tMin: FloatContainer,
                            record: Intersection, eps: EpsilonTable): Boolean {
        val x = ray.origin.x - this.center.x
        val y = ray.origin.y - this.center.y
        val z = ray.origin.z - this.center.z

        val t = this.normal.dot(x, y, z) / ray.dir.dot(this.normal)

        if(t <= eps[ID]) {
            return false
        }

        ray.projectAlong(t, record.localPosition)

        val pX       = ray.origin.x + t * ray.dir.x
        val pY       = ray.origin.x + t * ray.dir.x
        val pZ       = ray.origin.x + t * ray.dir.x
        val rSquared = this.radius * this.radius
        val dToC     = this.center.distanceSquaredTo(pX, pY, pZ)

        return when (dToC >= rSquared) {
            false -> {
                record.localPosition.set(pX, pY, pZ)
                record.normal.set(this.normal)
                record.worldPosition.set(pX, pY, pZ)

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

        val t = this.normal.dot(x, y, z) / ray.dir.dot(this.normal)

        if(t <= eps[ID]) {
            return false
        }

        val pX       = ray.origin.x + t * ray.dir.x
        val pY       = ray.origin.x + t * ray.dir.x
        val pZ       = ray.origin.x + t * ray.dir.x
        val rSquared = this.radius * this.radius

        return when(this.center.distanceSquaredTo(pX, pY, pZ) < rSquared &&
                    tMax > t) {
            false -> false
            true  -> {
                tMin.value = t

                true
            }
        }
    }
}