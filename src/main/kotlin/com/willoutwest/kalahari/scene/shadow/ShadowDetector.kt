package com.willoutwest.kalahari.scene.shadow

import com.willoutwest.kalahari.math.EpsilonTable
import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.Vector3
import com.willoutwest.kalahari.scene.Actor
import com.willoutwest.kalahari.scene.light.Light

/**
 * Represents a mechanism for detecting whether or not points, or objects, are
 * shadowed (do not receive light) in a scene.
 */
interface ShadowDetector {

    /**
     * Determines whether or not the specified point is in shadow relative to
     * a camera and therefore hidden by an object in the specified scene.
     *
     * @param light
     *        The light casting the shadow(s).
     * @param worldPosition
     *        The un-transformed world position
     * @param omegaI
     *        The incident light direction.
     * @param tS
     *        The maximum allowed parametric time for a shadow-ray
     *        intersection.
     * @param root
     *        The root of the scene to render.
     * @param eps
     *        The collection of epsilon values to use for determining a
     *        successful intersection.
     * @return Whether or not a point is in shadow.
     */
    fun isInShadow(light: Light,
                   worldPosition: Point3,
                   omegaI: Vector3,
                   tS: Float,
                   root: Actor,
                   eps: EpsilonTable): Boolean
}