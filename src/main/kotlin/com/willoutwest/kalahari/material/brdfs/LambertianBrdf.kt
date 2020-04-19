package com.willoutwest.kalahari.material.brdfs

import com.willoutwest.kalahari.material.Brdf
import com.willoutwest.kalahari.material.Material
import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.ComputeUtils
import com.willoutwest.kalahari.math.MathUtils
import com.willoutwest.kalahari.math.Vector3
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.util.FloatContainer

/**
 * Represents an implementation of [Brdf] that uses perfect diffuse
 * reflection to model the equal scattering of light off of arbitrary
 * geometric surfaces.
 */
class LambertianBrdf : AbstractBrdf(), Brdf {

    override fun estimatef(material: Material, record: Intersection,
                           omegaI: Vector3, omegaNot: Vector3,
                           pdf: FloatContainer, store: Color3): Color3 {
        val cache = ComputeUtils.localCache

        val u     = cache.vectors.borrow()
        val v     = cache.vectors.borrow()
        val w     = cache.vectors.borrow()
        val sp    = material.sampler.nextSample()

        w.set(record.normal)
        v.set(Vector3.Jitter)
            .crossSelf(w)
            .normalizeSelf()
        u.set(v)
            .crossSelf(w)

        omegaI.set(u)
            .timesSelf(sp.x)
            .plusTimesSelf(sp.y, v)
            .plusTimesSelf(sp.z, w)
            .normalizeSelf()

        pdf.value = omegaI.dot(record.normal) * MathUtils.InvPi

        cache.vectors.reuse(u, v, w)

        return material.cD.getColor(record, store)
            .timesSelf(material.kD * MathUtils.InvPi)
    }

    override fun f(material: Material, record: Intersection, omegaI: Vector3,
                   omegaNot: Vector3, store: Color3): Color3 =
        material.cD.getColor(record, store)
            .timesSelf(material.kD * MathUtils.InvPi)

    override fun rho(material: Material, record: Intersection,
                     omegaNot: Vector3, store: Color3): Color3 =
        material.cD.getColor(record, store)
            .timesSelf(material.kD)
}