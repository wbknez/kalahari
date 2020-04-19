package com.willoutwest.kalahari.material

import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.Vector3
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.util.FloatContainer

/**
 * Represents a mechanism to describe how light is reflected off the surface of
 * an arbitrary geometric surface.
 */
interface Brdf {

    /**
     * Computes the bihemispheric (hemisphere-hemisphere) reflectance along
     * the specified refracted direction.
     *
     * @param material
     *        The collection of rendering parameters to use.
     * @param record
     *        The geometric surface to compute the radiance for, given as a
     *        successful intersection with a cast ray.
     * @param omegaI
     *        The incident light direction.
     * @param omegaNot
     *        The refracted light direction.
     * @param pdf
     *        The probability density function that describes a material
     *        surface.
     * @param store
     *        The color to store the result in.
     * @return A reference to store for easy chaining.
     */
    fun estimatef(material: Material, record: Intersection, omegaI: Vector3,
                  omegaNot: Vector3, pdf: FloatContainer,
                  store: Color3): Color3

    /**
     * Computes the contribution of irradiance from the specified incident
     * direction to reflected radiance along the specified refracted direction.
     *
     * @param material
     *        The collection of rendering parameters to use.
     * @param record
     *        The geometric surface to compute the radiance for, given as a
     *        successful intersection with a cast ray.
     * @param omegaI
     *        The incident light direction.
     * @param omegaNot
     *        The refracted light direction.
     * @param store
     *        The color to store the result in.
     * @return A reference to store for easy chaining.
     */
    fun f(material: Material, record: Intersection, omegaI: Vector3,
          omegaNot: Vector3, store: Color3): Color3

    /**
     * Computes the bihemispheric (hemisphere-hemisphere) reflectance along
     * the specified refracted direction
     *
     * @param material
     *        The collection of rendering parameters to use.
     * @param record
     *        The geometric surface to compute the radiance for, given as a
     *        successful intersection with a cast ray.
     * @param omegaNot
     *        The refracted light direction.
     * @param store
     *        The color to store the result in.
     * @return A reference to store for easy chaining.
     */
    fun rho(material: Material, record: Intersection, omegaNot: Vector3,
            store: Color3): Color3

    /**
     * Computes both the direction of reflected rays using (usually) hemispheric
     * sampling and the contribution of irradiance along the incident
     * direction to the reflected radiance.
     *
     * @param material
     *        The collection of rendering parameters to use.
     * @param record
     *        The geometric surface to compute the radiance for, given as a
     *        successful intersection with a cast ray.
     * @param omegaI
     *        The incident light direction.
     * @param omegaNot
     *        The refracted light direction.
     * @param store
     *        The color to store the result in.
     * @return A reference to store for easy chaining.
     */
    fun samplef(material: Material, record: Intersection,
                omegaI: Vector3, omegaNot: Vector3, store: Color3): Color3
}