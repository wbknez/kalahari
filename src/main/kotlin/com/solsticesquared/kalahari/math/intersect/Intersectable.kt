package com.solsticesquared.kalahari.math.intersect

import com.solsticesquared.kalahari.math.EpsilonTable
import com.solsticesquared.kalahari.math.Ray3
import com.solsticesquared.kalahari.util.container.FloatContainer

/**
 * Represents object whose geometric bounds may be tested, and thereby
 * traced, using rays.
 */
interface Intersectable {

    /**
     * Performs a ray intersecton test against this object's geometry.
     *
     * @param ray
     *        The ray to test against.
     * @param tMin
     *        The point of intersection given in terms of parametric time.
     * @param record
     *        The intersection to use.  If there is a successful
     *        intersection, then this structure will be filled with all
     *        relevant data.  If not, it will be left untouched.
     * @param eps
     *        The collection of epsilon values to use for determining a
     *        successful intersection.
     * @return Whether or not an intersection occurred.
     */
    fun intersect(ray: Ray3, tMin: FloatContainer, record: Intersection,
                  eps: EpsilonTable): Boolean
}
