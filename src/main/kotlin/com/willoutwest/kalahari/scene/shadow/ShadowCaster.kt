package com.willoutwest.kalahari.scene.shadow

import com.willoutwest.kalahari.math.EpsilonTable
import com.willoutwest.kalahari.math.Ray3
import com.willoutwest.kalahari.util.FloatContainer
import com.willoutwest.kalahari.util.ObjectContainer

/**
 * Represents an object whose geometric bounds may be tested, and thereby
 * traced, using shadow-rays to determine whether or not they interfere with
 * the trajectory of radiance.
 *
 * This interference is commonly called "shadowing" and results in visually
 * darker regions where one object prohibits light rays from reaching another.
 */
interface ShadowCaster {

    /**
     * Performs a shadow-ray intersection test against this object's geometry.
     *
     * @param ray
     *        The shadow ray to test against.
     * @param tMin
     *        The point of intersection given in terms of parametric time.
     * @param obj
     *        The intersecting surface (actor).
     * @param eps
     *        The collection of epsilon values to use for determining a
     *        successful intersection.
     * @param tMax
     *        The maximum allowed parametric time for a shadow-ray
     *        intersection.
     * @return Whether or not a shadow-ray intersection occurred.
     */
    fun shadows(ray: Ray3, tMin: FloatContainer, obj: ObjectContainer,
                eps: EpsilonTable, tMax: Float = Float.MAX_VALUE): Boolean
}
