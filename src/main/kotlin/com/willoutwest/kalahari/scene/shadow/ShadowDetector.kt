package com.willoutwest.kalahari.scene.shadow

import com.willoutwest.kalahari.math.EpsilonTable
import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.Vector3
import com.willoutwest.kalahari.scene.Scene

/**
 * Represents a mechanism for detecting whether or not points, or objects, are
 * shadowed (do not receive light) in a scene.
 */
interface ShadowDetector {

    /**
     * Determines whether or not the specified point is visible to the camera
     * and not hidden by an object in the specified scene.
     *
     * @param worldPosition
     *        The un-transformed world position
     * @param omegaI
     *        The incident light direction.
     * @param tS
     *        The maximum allowed parametric time for a shadow-ray
     *        intersection.
     * @param scene
     *        The scene to render.
     * @param eps
     *        The collection of epsilon values to use for determining a
     *        successful intersection.
     * @return Whether or not a point is visible.
     */
    fun isVisible(worldPosition: Point3, omegaI: Vector3, tS: Float,
                  scene: Scene, eps: EpsilonTable): Boolean
}