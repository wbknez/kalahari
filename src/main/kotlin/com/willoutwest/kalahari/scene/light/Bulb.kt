package com.willoutwest.kalahari.scene.light

import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.Vector3
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.scene.Actor

/**
 * Represents a mechanism for modeling the radiance output of an arbitrary
 * light source.
 */
interface Bulb {

    /**
     * Computes a partial cosine factor (n <b>dot</b> omegaI) for the area
     * form of the rendering equation.
     *
     * This method is for use with area lighting only.
     *
     * @param light
     *        The collection of lighting parameters to use.
     * @param record
     *        The surface to compute the radiance for, given as a successful
     *        intersection against a cast ray.
     * @return A partial cosine factor.
     */
    fun G(light: Light, record: Intersection): Float

    /**
     * Computes the direction of a shadow ray in order to test whether or
     * not an object is lit.
     *
     * @param light
     *        The collection of lighting parameters to use.
     * @param record
     *        The surface to compute the radiance for, given as a successful
     *        intersection against a cast ray.
     * @param store
     *        The vector to store the result in.
     * @return A reference to store for easy chaining.
     */
    fun illuminate(light: Light, record: Intersection, store: Vector3): Vector3

    /**
     * Computes both the color and amount of radiance emitted by the
     * specified light.
     *
     * Please note that intensity is given as a greyscale color quantity.
     *
     * @param light
     *        The collection of lighting parameters to use.
     * @param root
     *        The scenegraph root to use.
     * @param record
     *        The surface to compute the radiance for, given as a successful
     *        intersection against a cast ray.
     * @param store
     *        The color to store the result in.
     * @return A reference to store for easy chaining.
     */
    fun L(light: Light, root: Actor, record: Intersection, store: Color3):
        Color3

    /**
     * Returns the probability density function that describes the finite
     * surface, if any, being used as the starting point for light rays cast
     * into a scene.
     *
     * This method is for use with area lighting only.
     *
     * @param light
     *        The collection of lighting parameters to use.
     * @param record
     *        The surface to compute the radiance for, given as a successful
     *        intersection against a cast ray.
     * @return A probability density function.
     */
    fun pdf(light: Light, record: Intersection): Float
}