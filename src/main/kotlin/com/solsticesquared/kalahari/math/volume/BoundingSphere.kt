package com.solsticesquared.kalahari.math.volume

import com.solsticesquared.kalahari.math.MathUtils
import com.solsticesquared.kalahari.math.Point3
import com.solsticesquared.kalahari.math.Ray3
import com.solsticesquared.kalahari.util.hash

/**
 * Represents an implementation of [BoundingVolume] that is defined as a
 * simple sphere.
 *
 * @property radius
 *           The radius of the sphere.
 */
class BoundingSphere(val radius: Float = 1f,
                     override val center: Point3 = Point3.Zero.clone())
    : BoundingVolume, Cloneable {

    override val max: Point3
        get() = Point3(
            this.center.x + this.radius,
            this.center.y + this.radius,
            this.center.z + this.radius
                      )

    override val min: Point3
        get() = Point3(
            this.center.x - this.radius,
            this.center.y - this.radius,
            this.center.z - this.radius
                      )

    constructor(sphere: BoundingSphere?) : this(sphere!!.radius, sphere.center)

    override fun clone(): BoundingSphere = BoundingSphere(this)

    override fun contains(point: Point3): Boolean {
        val dist = this.center.distanceSquaredTo(point)
        return dist < this.radius * this.radius
    }

    override fun equals(other: Any?): Boolean =
        when(other) {
            is BoundingSphere -> this.radius == other.radius &&
                                 this.center == other.center
            else              -> false
        }

    override fun hashCode(): Int = hash(this.radius, this.center)

    override fun intersects(ray: Ray3): Boolean {
        // The algorithm to model bounding sphere to ray intersections uses
        // nothing more than basic algebra and vector analysis to solve the
        // resulting quadratic equation obtained by inserting the equation of
        // a ray into the equation of a sphere.
        //
        // Courtesy of:
        //  [1] Paul Bourke (1992), "Intersection of a Line and a Sphere (or a
        //  circle)".
        //  [2] University of Maryland Baltimore County, "Ray-Sphere
        //  Intersection".
        //  [3] RMIT Australia (Haines), "Intersection Detection".
        val dX = this.center.x - ray.origin.x
        val dY = this.center.y - ray.origin.y
        val dZ = this.center.z - ray.origin.z

        val dDotD    = dX * ray.dir.x + dY * ray.dir.y +
                       dZ * ray.dir.z
        val dSquared = dX * dX + dY * dY + dZ * dZ
        val rSquared = this.radius * this.radius

        return !(dDotD < 0f && dSquared > rSquared) &&
               (dSquared - (dDotD * dDotD)) <= rSquared
    }

    override fun intersects(volume: BoundingVolume): Boolean {
        return volume.intersectsSphere(this)
    }

    override fun intersectsBox(box: BoundingBox): Boolean {
        // The algorithm to model bounding sphere to bounding box
        // intersections exploits simple distance computations to determine
        // whether or not they overlap.
        //
        // In brief, a sphere and a box intersect if the distance from the
        // center of the sphere to one of the extreme points on the box is
        // less than or equal to the radius of the sphere.
        //
        // Courtesy of Jim Arvo, "Graphics Gems 2".
        var dist = this.radius * this.radius
        val maxX = box.center.x + box.xLen
        val maxY = box.center.y + box.yLen
        val maxZ = box.center.z + box.zLen
        val minX = box.center.x - box.xLen
        val minY = box.center.y - box.yLen
        val minZ = box.center.z - box.zLen

        if(this.center.x < minX) {
            dist -= MathUtils.pow(this.center.x - minX, 2f)
        }
        else if(this.center.x > maxX) {
            dist -= MathUtils.pow(this.center.x - maxX, 2f)
        }

        if(this.center.y < minY) {
            dist -= MathUtils.pow(this.center.y - minY, 2f)
        }
        else if(this.center.y > maxY) {
            dist -= MathUtils.pow(this.center.y - maxY, 2f)
        }

        if(this.center.z < minZ) {
            dist -= MathUtils.pow(this.center.z - minZ, 2f)
        }
        else if(this.center.z > maxZ) {
            dist -= MathUtils.pow(this.center.z - maxZ, 2f)
        }

        return dist >= 0f
    }

    override fun intersectsSphere(sphere: BoundingSphere): Boolean {
        // The algorithm to model bounding sphere to bounding sphere
        // intersections is very simple, which is why bounding spheres are so
        // nice to work with.
        //
        // In brief, the spheres intersect if the distance between their
        // centers is less than or equal to the sum of their combined radii.
        // This can be shortened by using the squared distance and comparing
        // to the squared radii; this adds a multiplication but spares a
        // square root.
        //
        // Courtesy of Gamasutra, "Simple Intersection Tests for Games"
        val dist = this.center.distanceSquaredTo(sphere.center)
        val radii = this.radius + sphere.radius
        return dist <= (radii * radii)
    }

    override fun toString(): String =
        "(%f, %s)".format(this.radius, this.center)
}
