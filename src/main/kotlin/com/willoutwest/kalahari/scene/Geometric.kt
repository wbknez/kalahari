package com.willoutwest.kalahari.scene

import com.willoutwest.kalahari.material.Material
import com.willoutwest.kalahari.math.ComputeUtils
import com.willoutwest.kalahari.math.EpsilonTable
import com.willoutwest.kalahari.math.Quaternion
import com.willoutwest.kalahari.math.Ray3
import com.willoutwest.kalahari.math.Vector3
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.util.FloatContainer
import com.willoutwest.kalahari.util.ObjectContainer

/**
 * Represents an implementation of [Actor] that contains an arbitrary
 * geometric surface with associated visualization information.
 *
 * @property material
 *           The material to use.
 * @property surface
 *           The geometric surface to intersect.
 */
class Geometric(name: String, private val surface: Surface,
                var material: Material? = null)
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

        val cache = ComputeUtils.localCache
        val hRay  = cache.rays.borrow()

        hRay.set(ray).transformSelf(this.invTransform)

        val hit = this.surface.intersects(hRay, tMin, record, eps)

        if(hit) {
            record.normal.transformSelf(this.invTransform)
            record.obj = this
            record.worldPosition.transformSelf(this.invTransform)
        }

        cache.rays.reuse(hRay)

        return hit
    }

    override fun isReceivingShadows(): Boolean =
        super<AbstractActor>.isReceivingShadows() &&
        this.material?.isReceivingShadows() == true

    override fun move(x: Float, y: Float, z: Float): Geometric =
        super.move(x, y, z) as Geometric

    override fun move(vec: Vector3): Geometric = super.move(vec) as Geometric

    override fun rotate(angle: Float, axis: Vector3): Geometric =
        super.rotate(angle, axis) as Geometric

    override fun rotate(roll: Float, pitch: Float, yaw: Float): Geometric =
        super.rotate(roll, pitch, yaw) as Geometric

    override fun rotate(quat: Quaternion): Geometric =
        super.rotate(quat) as Geometric

    override fun scale(x: Float, y: Float, z: Float): Geometric =
        super.scale(x, y, z) as Geometric

    override fun scale(vec: Vector3): Geometric = super.scale(vec) as Geometric

    override fun shadows(ray: Ray3, tMin: FloatContainer, obj: ObjectContainer,
                         eps: EpsilonTable, tMax: Float): Boolean {
        if(this.bounds?.intersects(ray) == false) {
            return false
        }

        val cache = ComputeUtils.localCache
        val sRay  = cache.rays.borrow()

        sRay.set(ray).transformSelf(this.invTransform)

        val hit = this.surface.shadows(sRay, tMin, obj, eps, tMax)

        if(hit) {
            obj.obj = this
        }

        cache.rays.reuse(sRay)

        return hit
    }
}