package com.solsticesquared.kalahari.scene.surface

import com.solsticesquared.kalahari.math.Normal3
import com.solsticesquared.kalahari.math.Point3
import com.solsticesquared.kalahari.math.Ray3

/**
 * Represents a collection of utility methods for working with implicit
 * surfaces.
 */
sealed class SurfaceUtils {

    companion object {

        /**
         * Computes the parametric time of intersection between the specified
         * point with the specified surface normal and the specified ray.
         *
         * @param point
         *        The point to intersect with.
         * @param normal
         *        The surface normal to use.
         * @param ray
         *        The ray to intersect with.
         * @return The parametric time of intersection.
         */
        fun getIntersectionTime(point: Point3, normal: Normal3, ray: Ray3)
            : Float {
            val iX = point.x - ray.origin.x
            val iY = point.y - ray.origin.y
            val iZ = point.z - ray.origin.z
            return (iX * normal.x + iY * normal.y + iZ * normal.z) /
                   ray.dir.dot(normal)
        }
    }
}
