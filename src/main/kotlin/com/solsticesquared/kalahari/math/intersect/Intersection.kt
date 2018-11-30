package com.solsticesquared.kalahari.math.intersect

import com.solsticesquared.kalahari.math.Normal3
import com.solsticesquared.kalahari.math.Point3

/**
 * Represents the result of a ray intersecting with an arbitrary geometric
 * surface.
 *
 * @property depth
 *           The recursive trace depth an intersection occurred at.
 * @property localPosition
 *           The intersection point in local space.
 * @property normal
 *           The normal to an intersection.
 * @property obj
 *           User data (usually the intersecting surface or its material).
 * @property reversed
 *           Whether or not the intersection normal has been reversed.
 * @property worldPosition
 *           The intersection point in world space.
 */
data class Intersection(var depth: Int = 0,
                        val localPosition: Point3 = Point3(),
                        val normal: Normal3 = Normal3(),
                        var obj: Any? = null,
                        var reversed: Boolean = false,
                        val worldPosition: Point3 = Point3()) : Cloneable
