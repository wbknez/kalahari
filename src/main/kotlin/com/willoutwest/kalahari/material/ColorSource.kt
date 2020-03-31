package com.willoutwest.kalahari.material

import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.intersect.Intersection

/**
 * Represents a mechanism for computing both dynamic and static colors and
 * color patterns based on the results of the intersection between a ray and an
 * arbitrary geometric surface.
 */
@FunctionalInterface
interface ColorSource {

    /**
     * Computes the color resulting from the transformation of an
     * intersection between a ray and an arbitrary geometric surface to
     * an independent color space.
     *
     * @param record
     *        The geometric surface to compute the color for, given as a
     *        successful intersection with a cast ray.
     * @param store
     *        The color to store the result in.
     * @return A reference to [store] for easy chaining.
     */
    fun getColor(record: Intersection, store: Color3): Color3
}
