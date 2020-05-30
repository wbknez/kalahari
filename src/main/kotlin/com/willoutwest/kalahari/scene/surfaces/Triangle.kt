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
 * triangle.
 *
 * @property v0
 *           A vertex.
 * @property v1
 *           Another vertex.
 * @property v2
 *           Another vertex.
 * @property normal
 *           The normalized direction to face.
 */
data class Triangle(val v0: Point3 = Point3.Zero.clone(),
                    val v1: Point3 = Point3.Z.clone(),
                    val v2: Point3 = Point3.X.clone(),
                    val normal: Normal3 = Normal3(0f, 1f, 0f))
    : Cloneable, Surface {

    companion object {

        /**
         * Denotes a successful intersection with a normal, or "hit", ray.
         */
        const val HitEps    = 0.0001f

        /**
         * The unique identifier in an epsilon table.
         */
        const val ID        = "surf.tri"

        /**
         * Denotes a successful intersection with a reflected, or "shadow",
         * ray.
         */
        const val ShadowEps = 0.001f
    }

    /**
     * Constructor.
     *
     * Constructs the normal based on the specified points.
     *
     * @param v0
     *        A vertex to use.
     * @param v1
     *        Another vertex to use.
     * @param v2
     *        Another vertex to use.
     */
    constructor(v0: Point3, v1: Point3, v2: Point3)
        : this(v0, v1, v2, Normal3()) {
        this.normal.set(this.v1.x - this.v0.x, this.v1.y - this.v0.y,
                        this.v1.z - this.v0.z)
            .crossSelf(this.v2.x - this.v0.x, this.v2.y - this.v0.y,
                       this.v2.z - this.v0.z)
            .normalizeSelf()
    }

    /**
     * Constructor.
     *
     * @param tri
     *        The triangle to copy from.
     */
    constructor(tri: Triangle?) : this(tri!!.v0, tri.v1, tri.v2, tri.normal)

    override fun clone(): Triangle = Triangle(this)

    override fun intersects(ray: Ray3, tMin: FloatContainer,
                            record: Intersection, eps: EpsilonTable): Boolean {
        val a = this.v0.x - this.v1.x
        val b = this.v0.x - this.v2.x
        val c = ray.dir.x
        val d = this.v0.x - ray.origin.x
        val e = this.v0.y - this.v1.y
        val f = this.v0.y - this.v2.y
        val g = ray.dir.y
        val h = this.v0.y - ray.origin.y
        val i = this.v0.z - this.v1.z
        val j = this.v0.z - this.v2.z
        val k = ray.dir.z
        val l = this.v0.z - ray.origin.z

        val m = f * k - g * j
        val n = h * k - g * l
        val p = f * l - h * j
        val q = g * i - e * k
        val s = e * j - f * i

        val denom = a * m + b * q + c * s

        if(denom == 0f) {
            return false
        }

        val invDenom = 1f / denom
        val e1       = d * m - b * n - c * p
        val beta     = e1 * invDenom

        if(beta < 0f) {
            return false
        }

        val r     = e * l - h * i
        val e2    = a * n + d * q + c * r
        val gamma = e2 * invDenom

        if(gamma < 0f || (beta + gamma) > 1f) {
            return false
        }

        val e3   = a * p - b * r + d * s
        val t    = e3 * invDenom

        return when(t <= eps[ID]) {
            false -> {
                ray.projectAlong(t, record.localPosition)
                ray.projectAlong(t, record.worldPosition)

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
        val a = this.v0.x - this.v1.x
        val b = this.v0.x - this.v2.x
        val c = ray.dir.x
        val d = this.v0.x - ray.origin.x
        val e = this.v0.y - this.v1.y
        val f = this.v0.y - this.v2.y
        val g = ray.dir.y
        val h = this.v0.y - ray.origin.y
        val i = this.v0.z - this.v1.z
        val j = this.v0.z - this.v2.z
        val k = ray.dir.z
        val l = this.v0.z - ray.origin.z

        val m = f * k - g * j
        val n = h * k - g * l
        val p = f * l - h * j
        val q = g * i - e * k
        val s = e * j - f * i

        val denom = a * m + b * q + c * s

        if(denom == 0f) {
            return false
        }

        val invDenom = 1f / denom
        val e1       = d * m - b * n - c * p
        val beta     = e1 * invDenom

        if(beta < 0f) {
            return false
        }

        val r     = e * l - h * i
        val e2    = a * n + d * q + c * r
        val gamma = e2 * invDenom

        if(gamma < 0f || (beta + gamma) > 1f) {
            return false
        }

        val e3   = a * p - b * r + d * s
        val t    = e3 * invDenom

        return when(t > eps[ID] && tMax > t) {
            false -> false
            true  -> {
                tMin.value = t

                true
            }
        }
    }
}