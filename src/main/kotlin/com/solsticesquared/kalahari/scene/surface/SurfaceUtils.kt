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
         * This value is negative if the ray direction dotted with the normal
         * is zero.  This typically denotes that no intersection is possible
         * and allows this method to be used in an epsilon check.
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
            val iX    = point.x - ray.origin.x
            val iY    = point.y - ray.origin.y
            val iZ    = point.z - ray.origin.z
            val dDotN = ray.dir.dot(normal)

            return when(dDotN != 0f) {
                false -> -1f
                true  -> (iX * normal.x + iY * normal.y + iZ * normal.z) / dDotN
            }
        }

        fun solveQuartic(a: Float, b: Float, c: Float, d: Float, e: Float)
            : FloatArray {
            // FIXME: Implement me!
            return floatArrayOf(0f)
        }

        fun solveQuartic(coeffs: FloatArray): FloatArray {
            return solveQuartic(coeffs[0], coeffs[1], coeffs[2], coeffs[3],
                                coeffs[4])
        }
    }
}
