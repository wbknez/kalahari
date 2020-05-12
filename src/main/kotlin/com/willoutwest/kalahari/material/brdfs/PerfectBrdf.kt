package com.willoutwest.kalahari.material.brdfs

import com.willoutwest.kalahari.material.Brdf
import com.willoutwest.kalahari.material.Material
import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.Vector3
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.util.FloatContainer

/**
 * Represents an implementation of [Brdf] that uses perfect (non-sampled)
 * specular reflection to model the reflective properties of light off of
 * arbitrary geometric surfaces.
 */
class PerfectBrdf : AbstractBrdf(), Brdf {

    override fun estimatef(material: Material, record: Intersection,
                           omegaI: Vector3, omegaNot: Vector3,
                           pdf: FloatContainer, store: Color3): Color3 {
        val nDotNot = record.normal.dot(omegaNot)

        omegaI.set(omegaNot)
            .negateSelf()
            .plusTimesSelf(2f * nDotNot, record.normal)

        pdf.value = record.normal.dot(omegaI)

        return material.cR.getColor(record, store)
            .timesSelf(material.kR)
    }

    override fun samplef(material: Material, record: Intersection,
                         omegaI: Vector3, omegaNot: Vector3,
                         store: Color3): Color3 {
        val nDotNot = record.normal.dot(omegaNot)

        omegaI.set(omegaNot)
            .negateSelf()
            .plusTimesSelf(2f * nDotNot, record.normal)

        return material.cR.getColor(record, store)
            .timesSelf(material.kR)
            .divSelf(record.normal.dot(omegaI))
    }
}