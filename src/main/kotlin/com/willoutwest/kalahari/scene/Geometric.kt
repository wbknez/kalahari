package com.willoutwest.kalahari.scene

import com.willoutwest.kalahari.math.EpsilonTable
import com.willoutwest.kalahari.math.Ray3
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.util.FloatContainer

/**
 * Represents an implementation of [Actor] that contains an arbtirary
 * geometric surface with associated visualization information.
 */
class Geometric(name: String, private val surface: Surface)
    : AbstractActor(name), Actor, Cloneable {

    /**
     * Constructor.
     *
     * @param geom
     *        The geometric actor to copy from.
     */
    constructor(geom: Geometric?) : this(geom!!.name, geom.surface)

    override fun clone(): Geometric = Geometric(this)

    override fun intersects(ray: Ray3, tMin: FloatContainer,
                            record: Intersection, eps: EpsilonTable): Boolean {
        if(this.bounds?.intersects(ray) == false) {
            return false
        }

        return when(this.surface.intersects(ray, tMin, record, eps)) {
            false -> false
            true  -> {
                record.obj = this
                true
            }
        }
    }
}