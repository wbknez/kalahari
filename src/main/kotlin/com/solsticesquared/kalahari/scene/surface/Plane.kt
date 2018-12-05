package com.solsticesquared.kalahari.scene.surface

import com.solsticesquared.kalahari.math.EpsilonTable
import com.solsticesquared.kalahari.math.Normal3
import com.solsticesquared.kalahari.math.Point3
import com.solsticesquared.kalahari.math.Ray3
import com.solsticesquared.kalahari.math.intersect.Intersection
import com.solsticesquared.kalahari.scene.GeometricSurface
import com.solsticesquared.kalahari.scene.surface.SurfaceUtils.Companion.getIntersectionTime
import com.solsticesquared.kalahari.util.container.FloatContainer

/**
 * Represents an implementation of [GeometricSurface] that is defined as an
 * implicit plane.
 *
 * @property normal
 *           The "front" of the plane.
 * @property point
 *           An arbitrary point.
 */
data class Plane(val normal: Normal3 = Normal3.Y.clone(),
                 val point: Point3 = Point3.Zero.clone())
    : Cloneable, GeometricSurface {

    companion object {

        /**
         * Denotes a successful intersection with a normal, or "hit", ray.
         */
        const val hEps  = 0.0001f

        /**
         * The unique identifier in an epsilon table.
         */
        const val ID    = "geom.plane"
    }

    /**
     * Constructor.
     *
     * @param nX
     *        The x-axis coordinate of the normal to use.
     * @param nY
     *        The y-axis coordinate of the normal to use.
     * @param nZ
     *        The z-axis coordinate of the normal to use.
     * @param x
     *        The x-axis coordinate of the point to use.
     * @param y
     *        The y-axis coordinate of the point to use.
     * @param z
     *        The z-axis coordinate of the point to use.
     */
    constructor(nX: Float = 0.0f, nY: Float = 1.0f, nZ: Float = 0.0f,
                x: Float = 0.0f, y: Float = 0.0f, z: Float = 0.0f)
        : this(Normal3(nX, nY, nZ), Point3(x, y, z))

    /**
     * Constructor.
     *
     * @param plane
     *        The plane to copy from.
     */
    constructor(plane: Plane?) : this(plane!!.normal, plane.point)

    public override fun clone(): Plane = Plane(this)

    override fun intersect(ray: Ray3, tMin: FloatContainer,
                           record: Intersection, eps: EpsilonTable): Boolean {
        val hEps = eps[Plane.ID]
        val t    = getIntersectionTime(this.point, this.normal, ray)

        return when(t <= hEps) {
            false -> {
                record.localPosition.setFromProjection(ray, t)
                record.normal.set(this.normal)
                record.reversed = false
                record.worldPosition.setFromProjection(ray, t)
                tMin.value = t

                true
            }
            true  -> false
        }
    }
}
