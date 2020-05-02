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
 * Represents an implementation of [Brdf] that uses glossy (sampled) specular
 * reflection to model the unequal scattering of light off of arbitrary
 * geometric surfaces.
 */
class GlossyBrdf : AbstractBrdf(), Brdf {

    override fun estimatef(material: Material, record: Intersection,
                           omegaI: Vector3, omegaNot: Vector3,
                           pdf: FloatContainer, store: Color3): Color3 {
        val cache = ComputeUtils.localCache

        val r     = cache.vectors.borrow()
        val u     = cache.vectors.borrow()
        val v     = cache.vectors.borrow()
        val w     = cache.vectors.borrow()
        val sp    = material.sampler.nextSample()

        val nDotNot = record.normal.dot(omegaNot)

        r.set(omegaNot)
            .negateSelf()
            .plusTimesSelf(2f * nDotNot, record.normal)
        w.set(r)
        u.set(0.00424f, 1.0f, 0.00764f)
            .crossSelf(w)
            .normalizeSelf()
        v.set(u)
            .crossSelf(w)

        omegaI.set(u)
            .timesSelf(sp.x)
            .plusTimesSelf(sp.y, v)
            .plusTimesSelf(sp.z, w)

        if(record.normal.dot(omegaI) < 0f) {
            omegaI.set(u)
                .timesSelf(-sp.x)
                .plusTimesSelf(-sp.y, v)
                .plusTimesSelf(sp.z, w)
        }

        val phongLobe = MathUtils.pow(r.dot(omegaI), material.expS)

        pdf.value = phongLobe * record.normal.dot(omegaI)

        cache.vectors.reuse(r, u, v, w)

        return material.cS.getColor(record, store)
            .timesSelf(material.kS)
            .timesSelf(phongLobe)
    }

    override fun f(material: Material, record: Intersection, omegaI: Vector3,
                   omegaNot: Vector3, store: Color3): Color3 {
        val nDotI   = record.normal.dot(omegaI)
        val rX      = -omegaI.x + 2f * nDotI * record.normal.x
        val rY      = -omegaI.y + 2f * nDotI * record.normal.y
        val rZ      = -omegaI.z + 2f * nDotI * record.normal.z
        val rDotNot = omegaNot.dot(rX, rY, rZ)

        store.set(Color3.Black)

        if(rDotNot > 0f) {
            material.cS.getColor(record, store)
                .timesSelf(material.kS)
                .timesSelf(MathUtils.pow(rDotNot, material.exp))
        }

        return store
    }
}