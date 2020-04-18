package com.willoutwest.kalahari.math.intersect

import com.willoutwest.kalahari.math.Normal3
import com.willoutwest.kalahari.math.Point3

/**
 * Represents the result of a ray intersecting with an arbitrary geometric
 * surface.
 *
 * @property depth
 *           The recursive trace depth an intersection occurred at.
 * @property localPosition
 *           The intersection point in local space.
 * @property normal
 *           The normal to an intersection.
 * @property obj
 *           User data (usually the intersecting surface or its material).
 * @property reversed
 *           Whether or not the intersection normal has been reversed.
 * @property worldPosition
 *           The intersection point in world space.
 */
data class Intersection(@JvmField var depth: Int = 0,
                        @JvmField val localPosition: Point3 = Point3(),
                        @JvmField val normal: Normal3 = Normal3(),
                        @JvmField var obj: Any? = null,
                        @JvmField var reversed: Boolean = false,
                        @JvmField val worldPosition: Point3 = Point3())
    : Cloneable {

    /**
     * Constructor.
     *
     * @param record
     *        The intersection record to copy from.
     */
    constructor(record: Intersection?)
        : this(record!!.depth, record.localPosition.clone(),
               record.normal.clone(), record.obj, record.reversed,
               record.worldPosition.clone())

    override fun clone(): Intersection = Intersection(this)

    /**
     * Sets the properties of this record to those of the specified one.
     *
     * @param record
     *        The record to copy from.
     */
    fun set(record: Intersection) {
        this.depth    = record.depth
        this.obj      = record.obj
        this.reversed = record.reversed

        this.localPosition.set(record.localPosition)
        this.normal.set(record.normal)
        this.worldPosition.set(record.worldPosition)
    }
}