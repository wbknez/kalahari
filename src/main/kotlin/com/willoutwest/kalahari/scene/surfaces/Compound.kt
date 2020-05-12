package com.willoutwest.kalahari.scene.surfaces

import com.willoutwest.kalahari.math.ComputeUtils
import com.willoutwest.kalahari.math.EpsilonTable
import com.willoutwest.kalahari.math.Ray3
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.scene.Surface
import com.willoutwest.kalahari.util.FloatContainer
import com.willoutwest.kalahari.util.ObjectContainer

/**
 * Represents an implementation of [Surface] that is defined as the
 * intersection of a collection of other surfaces.
 */
data class Compound(private val surfaces: List<Surface>)
    : Cloneable, Surface {

    /**
     * Constructor.
     */
    constructor(compound: Compound?) : this(compound!!.surfaces)

    override fun clone(): Compound = Compound(this)

    override fun intersects(ray: Ray3, tMin: FloatContainer,
                            record: Intersection, eps: EpsilonTable): Boolean {
        val cache   = ComputeUtils.localCache
        val hRecord = cache.records.borrow()

        var hit     = false
        var minTime = Float.MAX_VALUE

        this.surfaces.forEach {
            if(it.intersects(ray, tMin, record, eps) && tMin.value < minTime) {
                hit     = true
                minTime = tMin.value

                hRecord.set(record)
            }
        }

        if(hit) {
            record.set(hRecord)

            tMin.value = minTime
        }

        cache.records.reuse(hRecord)

        return hit
    }

    override fun shadows(ray: Ray3, tMin: FloatContainer, obj: ObjectContainer,
                         eps: EpsilonTable, tMax: Float): Boolean {
        return this.surfaces.any { it.shadows(ray, tMin, obj, eps, tMax) }
    }
}