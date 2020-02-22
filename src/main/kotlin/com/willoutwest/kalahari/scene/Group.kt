package com.willoutwest.kalahari.scene

import com.willoutwest.kalahari.math.CacheUtils
import com.willoutwest.kalahari.math.EpsilonTable
import com.willoutwest.kalahari.math.Quaternion
import com.willoutwest.kalahari.math.Ray3
import com.willoutwest.kalahari.math.Vector3
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.util.FloatContainer

/**
 * Represents an implementation of [Actor] that contains a collection of
 * other actors as an arbitrary logical or spatial grouping.
 *
 * @property children
 *           A collection of actors.
 */
class Group(name: String) : AbstractActor(name), Actor, Cloneable {

    private val children = mutableListOf<Actor>()

    /**
     * Constructor.
     *
     * @param group
     *        The group to copy from.
     */
    constructor(group: Group?) : this(group!!.name) {
        this.children.addAll(group.children)
    }

    /**
     * Adds the specified child to this group's collection of children.
     *
     * @param child
     *        The child to add.
     */
    fun addChild(child: Actor) {
        child.parent = this

        this.children.add(child)
    }

    override fun clone(): Group = Group(this)

    override fun intersects(ray: Ray3, tMin: FloatContainer,
                            record: Intersection, eps: EpsilonTable): Boolean {
        if(!this.enabled || this.children.isEmpty()) {
            return false
        }

        if(this.bounds?.intersects(ray) == false) {
            return false
        }

        val cache   = CacheUtils.localCache
        val hRecord = cache.records.borrow()

        var hit     = false
        var minTime = Float.MAX_VALUE

        this.children.forEach {
            if(it.intersects(ray, tMin, record, eps) && tMin.value <= minTime) {
                hit     = true
                minTime = tMin.value

                hRecord.set(record)
            }
        }

        if(hit) {
            tMin.value = minTime
        }

        cache.records.reuse(hRecord)

        return hit
    }

    override fun move(x: Float, y: Float, z: Float): Group =
        super.move(x, y, z) as Group

    override fun move(vec: Vector3): Group = super.move(vec) as Group

    override fun rotate(angle: Float, axis: Vector3): Group =
        super.rotate(angle, axis) as Group

    override fun rotate(roll: Float, pitch: Float, yaw: Float): Group =
        super.rotate(roll, pitch, yaw) as Group

    override fun rotate(quat: Quaternion): Group = super.rotate(quat) as Group

    override fun scale(x: Float, y: Float, z: Float): Group =
        super.scale(x, y, z) as Group

    override fun scale(vec: Vector3): Group = super.scale(vec) as Group
}