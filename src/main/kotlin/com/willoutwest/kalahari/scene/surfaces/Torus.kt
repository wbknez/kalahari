package com.willoutwest.kalahari.scene.surfaces

import com.willoutwest.kalahari.math.EpsilonTable
import com.willoutwest.kalahari.math.Normal3
import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.PolyUtils
import com.willoutwest.kalahari.math.Ray3
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.scene.Surface
import com.willoutwest.kalahari.util.FloatContainer
import com.willoutwest.kalahari.util.ObjectContainer

/**
 * Represents an implementation of [Surface] that is defined as an implicit
 * sphere.
 *
 * @property a
 *           The swept radius.
 * @property b
 *           The tube radius.
 */
data class Torus(val a: Float, val b: Float) : Cloneable, Surface {

    companion object {

        /**
         * Denotes a successful intersection with a normal, or "hit", ray.
         */
        const val HitEps    = 0.0001f

        /**
         * The unique identifier in an epsilon table.
         */
        const val ID        = "surf.torus"

        /**
         * Denotes a successful intersection with a reflected, or "shadow",
         * ray.
         */
        const val ShadowEps = 0.001f
    }

    constructor(torus: Torus?) : this(torus!!.a, torus.b)

    override fun clone(): Torus = Torus(this)

    /**
     * Computes the normal the specified hit point on this torus.
     *
     * @param point
     *        The hit point to use.
     * @param store
     *        The normal to store the result in.
     */
    fun computeNormal(point: Point3, store: Normal3) {
        val a2 = this.a * this.a
        val b2 = this.b * this.b

        val x  = point.x
        val x2 = point.x * point.x
        val y  = point.y
        val y2 = point.y * point.y

        val z  = point.z
        val z2 = point.z * point.z

        val x2y2z2 = x2 + y2 + z2

        store.set(
            4 * x * (x2y2z2 - (a2 + b2)),
            4 * y * (x2y2z2 - (a2 + b2) + (2 * a2)),
            4 * z * (x2y2z2 - (a2 + b2))
        )
    }

    override fun intersects(ray: Ray3, tMin: FloatContainer,
                            record: Intersection, eps: EpsilonTable): Boolean {
        val aSquared  = this.a * this.a
        val bSquared  = this.b * this.b
        val faSquared = 4f * aSquared

        val dDotD = ray.dir.dot(ray.dir)
        val e     = ray.origin.x * ray.origin.x + ray.origin.y * ray.origin.y +
                    ray.origin.z * ray.origin.z - aSquared - bSquared
        val f     = ray.dir.dot(ray.origin.x, ray.origin.y, ray.origin.z)

        val roots = PolyUtils.quartic(
            dDotD * dDotD,
            4f * dDotD * f,
            2f * dDotD * e + 4f * f * f + faSquared * ray.dir.y * ray.dir.y,
            4f * f * e + 2f * faSquared * ray.origin.y * ray.dir.y,
            e * e - faSquared * (b * b - ray.origin.y * ray.origin.y)
        )

        if(roots.isEmpty()) {
            return false
        }

        var hit  = false
        var t    = Float.MAX_VALUE

        roots.forEach {
            if(it > eps[ID]) {
                hit = true

                if(it < t) {
                    t = it
                }
            }
        }

        return when(hit) {
            false -> false
            true  -> {
                ray.projectAlong(t, record.localPosition)
                ray.projectAlong(t, record.worldPosition)

                this.computeNormal(record.localPosition, record.normal)
                tMin.value = t

                true
            }
        }
    }

    override fun shadows(ray: Ray3, tMin: FloatContainer, obj: ObjectContainer,
                         eps: EpsilonTable, tMax: Float): Boolean {
        val aSquared  = this.a * this.a
        val bSquared  = this.b * this.b
        val faSquared = 4f * aSquared

        val dDotD = ray.dir.dot(ray.dir)
        val e     = ray.origin.x * ray.origin.x + ray.origin.y * ray.origin.y +
                    ray.origin.z * ray.origin.z - aSquared - bSquared
        val f     = ray.dir.dot(ray.origin.x, ray.origin.y, ray.origin.z)

        val roots = PolyUtils.quartic(
            dDotD * dDotD,
            4f * dDotD * f,
            2f * dDotD * e + 4f * f * f + faSquared * ray.dir.y * ray.dir.y,
            4f * f * e + 2f * faSquared * ray.origin.y * ray.dir.y,
            e * e - faSquared * (b * b - ray.origin.y * ray.origin.y)
        )

        val t = roots.find{ it > eps[ID] && it < tMax }

        return when(t != null) {
            false -> false
            true  -> {
                tMin.value = t

                true
            }
        }
    }
}