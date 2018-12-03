package com.solsticesquared.kalahari.scene.surface

import com.solsticesquared.kalahari.math.EpsilonTable
import com.solsticesquared.kalahari.math.MathUtils
import com.solsticesquared.kalahari.math.Point3
import com.solsticesquared.kalahari.math.Ray3
import com.solsticesquared.kalahari.math.intersect.Intersection
import com.solsticesquared.kalahari.scene.GeometricSurface
import com.solsticesquared.kalahari.util.container.FloatContainer

/**
 * Represents an implementation of [GeometricSurface] that is defined as a
 * implicit sphere.
 *
 * @property center
 *           The center point of the sphere.
 * @property radius
 *           The radius of the sphere.
 */
data class Sphere(val radius: Float = 1f,
                  val center: Point3 = Point3.Zero.clone())
    : Cloneable, GeometricSurface {

    companion object {

        /**
         * Denotes a successful intersection with a normal, or "hit", ray.
         */
        const val hEps  = 0.0001f

        /**
         * The unique identifier in an epsilon table.
         */
        const val ID    = "geom.sphere"
    }

    /**
     * Constructor.
     *
     * @param radius
     *        The radius to use.
     * @param x
     *        The x-axis coordinate of the center to use.
     * @param y
     *        The y-axis coordinate of the center to use.
     * @param z
     *        The z-axis coordinate of the center to use.
     */
    constructor(radius: Float = 1f, x: Float = 0.0f, y: Float = 0.0f,
                z: Float = 0.0f)
        : this(radius, Point3(x, y, z))

    /**
     * Constructor.
     *
     * @param sphere
     *        The sphere to copy from.
     */
    constructor(sphere: Sphere?) : this(sphere!!.radius, sphere.center)

    public override fun clone(): Sphere = Sphere(this)

    override fun intersect(ray: Ray3, tMin: FloatContainer, record: Intersection,
                           eps: EpsilonTable): Boolean {
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
        val hEps  = eps[Sphere.ID]

        var t = (-b - e) * denom

        if(t <= hEps) {
            t = (-b + e) * denom
        }

        if(t <= hEps) {
            return false
        }

        val invRadius = 1f / this.radius

        record.localPosition.setFromProjection(ray, t)
        record.normal.set((x + t * ray.dir.x) * invRadius,
                          (y + t * ray.dir.y) * invRadius,
                          (z + t * ray.dir.z) * invRadius)
        record.reversed = false
        record.worldPosition.setFromProjection(ray, t)
        tMin.value = t

        return true
    }
}
