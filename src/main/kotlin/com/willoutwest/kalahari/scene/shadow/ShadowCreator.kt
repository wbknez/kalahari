package com.willoutwest.kalahari.scene.shadow

import com.willoutwest.kalahari.math.Ray3
import com.willoutwest.kalahari.scene.light.Light

/**
 * Represents a mechanism to describe how light sources create shadows in
 * a scene by determining the maximum parametric time at which a shadow ray
 * intersection is valid.
 */
interface ShadowCreator {

    /**
     * Computes the maximum parametric time at which a shadow ray
     * intersection is legitimate.
     *
     * @param ray
     *        The shadow ray to test.
     * @param light
     *        The light source to use.
     * @return The maximum length of a shadow in parametric time.
     */
    fun shadowLength(ray: Ray3, light: Light): Float
}
