package com.solsticesquared.kalahari.math.volume

import com.solsticesquared.kalahari.math.Point3
import com.solsticesquared.kalahari.math.Ray3

/**
 * Represents a simple mathematical volume that may be used to define the
 * outermost bounds of an arbitrary geometric surface in order to optimize ray
 * intersection tests against it.
 *
 * Unlike the implementations of various geometric surfaces, bounding volumes
 * do not accept an epsilon parameter to denote valid intersections.  This is
 * because the bounding volumes implemented in this project are intended to
 * be approximate in comparison and therefore a potential loss of accuracy is
 * acceptable.
 *
 * @property center
 *           The center point of this bounding volume.
 * @property max
 *           The maximum point of this bounding volume.
 * @property min
 *           The minimum point of this bounding volume.
 */
interface BoundingVolume : Cloneable {

    val center: Point3

    val max: Point3

    val min: Point3

    public override fun clone(): BoundingVolume

    /**
     * Determines whether or not the specified point is contained within this
     * bounding volume.
     *
     * For purposes of this project, containment does not include the volume
     * boundary.
     *
     * @param point
     *        The point to check.
     * @return Whether or not a point is within a bounding volume.
     */
    fun contains(point: Point3): Boolean

    /**
     * Determines whether or not the specified ray intersects this bounding
     * volume within the specified epsilon.
     *
     * For purposes of this project, intersection includes the volume boundary.
     *
     * @param ray
     *        The ray to check.
     * @return Whether or not a ray intersects a bounding volume.
     */
    fun intersects(ray: Ray3): Boolean

    /**
     * Determines whether or not the specified bounding volume intersects
     * this one.
     *
     * For purposes of this project, intersection includes the volume boundary.
     *
     * @param volume
     *        The bounding volume to check.
     * @return Whether or not one bounding volume intersects another.
     */
    fun intersects(volume: BoundingVolume): Boolean

    /**
     * Determines whether or not the specified bounding box intersects
     * this bounding volume.
     *
     * For purposes of this project, intersection includes the volume boundary.
     *
     * @param box
     *        The bounding box to check.
     * @return Whether or not a bounding box intersects a volume.
     */
    fun intersectsBox(box: BoundingBox): Boolean

    /**
     * Determines whether or not the specified bounding sphere intersects
     * this bounding volume.
     *
     * For purposes of this project, intersection includes the volume boundary.
     *
     * @param sphere
     *        The bounding sphere to check.
     * @return Whether or not a bounding sphere intersects a volume.
     */
    fun intersectsSphere(sphere: BoundingSphere): Boolean
}
