package com.willoutwest.kalahari.material.brdfs

import com.willoutwest.kalahari.material.Brdf
import com.willoutwest.kalahari.material.Material
import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.Vector3
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.util.FloatContainer

/**
 * Represents an implementation of [Brdf] that provides a default
 * implementation for deriving classes.
 */
abstract class AbstractBrdf : Brdf {

    override fun estimatef(material: Material, record: Intersection,
                           omegaI: Vector3, omegaNot: Vector3,
                           pdf: FloatContainer, store: Color3): Color3 =
        store.set(Color3.Black)

    override fun f(material: Material, record: Intersection, omegaI: Vector3,
                   omegaNot: Vector3, store: Color3): Color3 =
        store.set(Color3.Black)

    override fun rho(material: Material, record: Intersection,
                     omegaNot: Vector3, store: Color3): Color3 =
        store.set(Color3.Black)

    override fun samplef(material: Material, record: Intersection,
                         omegaI: Vector3, omegaNot: Vector3,
                         store: Color3): Color3 =
        store.set(Color3.Black)
}