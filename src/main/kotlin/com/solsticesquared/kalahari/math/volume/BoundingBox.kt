package com.solsticesquared.kalahari.math.volume

import com.solsticesquared.kalahari.math.MathUtils
import com.solsticesquared.kalahari.math.Point3
import com.solsticesquared.kalahari.math.Ray3
import com.solsticesquared.kalahari.util.hash

/**
 * Represents an implementation of [BoundingVolume] that is defined as an
 * axis-aligned box.
 *
 * @property xLen
 *           The maximum length along the x-axis.
 * @property yLen
 *           The maximum length along the y-axis.
 * @property zLen
 *           The maximum length along the z-axis.
 */
class BoundingBox(val xLen: Float = 1f,
                  val yLen: Float = 1f,
                  val zLen: Float = 1f,
                  override val center: Point3 = Point3.Zero.clone())
    : BoundingVolume, Cloneable {

    override val max: Point3
        get() = Point3(
            this.center.x + this.xLen,
            this.center.y + this.yLen,
            this.center.z + this.zLen
                      )

    override val min: Point3
        get() = Point3(
            this.center.x - this.xLen,
            this.center.y - this.yLen,
            this.center.z - this.zLen
                      )

    /**
     * Constructor.
     *
     * @param xLen
     *           The maximum length along the x-axis to use.
     * @param yLen
     *           The maximum length along the y-axis to use.
     * @param zLen
     *           The maximum length along the z-axis to use.
     * @param x
     *        The x-axis coordinate of the center to use.
     * @param y
     *        The y-axis coordinate of the center to use.
     * @param z
     *        The z-axis coordinate of the center to use.
     */
    constructor(xLen: Float = 1f, yLen: Float = 1f, zLen: Float = 1f,
                x: Float = 0f, y: Float = 0f, z: Float = 0f)
        : this(xLen, yLen, zLen, Point3(x, y, z))

    /**
     * Constructor.
     *
     * @param box
     *        The bounding box to copy from.
     */
    constructor(box: BoundingBox?)
        : this(box!!.xLen, box.yLen, box.zLen, box.center.clone())

    override fun clone(): BoundingBox = BoundingBox(this)

    override fun contains(point: Point3): Boolean {
        val aX = MathUtils.abs(this.center.x - point.x)
        val aY = MathUtils.abs(this.center.y - point.y)
        val aZ = MathUtils.abs(this.center.z - point.z)
        return (aX < this.xLen) && (aY < this.yLen) && (aZ < this.zLen)
    }

    override fun equals(other: Any?): Boolean =
        when(other) {
            is BoundingBox -> this.xLen == other.xLen &&
                              this.yLen == other.yLen &&
                              this.zLen == other.zLen &&
                              this.center == other.center
            else           -> false
        }

    override fun hashCode(): Int =
        hash(this.xLen, this.yLen, this.zLen, this.center)

    override fun intersects(ray: Ray3): Boolean {
        // The algorithm to model bounding box to bounding box intersection
        // uses per-face intersection testing to determine whether a ray
        // intersects any part of the bounding box.
        //
        // Courtesy of Scratchpixel 2.0, "Ray-Box Intersection".
        val invX  = MathUtils.safeInverse(ray.dir.x)
        val invY  = MathUtils.safeInverse(ray.dir.y)

        val maxTx = when(invX >= 0) {
            false -> (this.center.x - this.xLen - ray.origin.x) * invX
            true  -> (this.center.x + this.xLen - ray.origin.x) * invX
        }
        val maxTy = when(invY >= 0) {
            false -> (this.center.y - this.yLen - ray.origin.y) * invY
            true  -> (this.center.y + this.yLen - ray.origin.y) * invY
        }
        val minTx = when(invX >= 0) {
            false -> (this.center.x + this.xLen - ray.origin.x) * invX
            true  -> (this.center.x - this.xLen - ray.origin.x) * invX
        }
        val minTy = when(invY >= 0) {
            false -> (this.center.y + this.yLen - ray.origin.y) * invY
            true  -> (this.center.y - this.yLen - ray.origin.y) * invY
        }

        if((minTx > maxTy) || (minTy > maxTx)) {
            return false
        }

        val invZ = MathUtils.safeInverse(ray.dir.z)

        val maxT  = when(maxTy < maxTx) {
            false -> maxTx
            true  -> maxTy
        }
        val maxTz = when(invZ >= 0) {
            false -> (this.center.z - this.zLen - ray.origin.z) * invZ
            true  -> (this.center.z + this.zLen - ray.origin.z) * invZ
        }
        val minT  = when(minTy > minTx) {
            false -> minTx
            true  -> minTy
        }
        val minTz = when(invZ >= 0) {
            false -> (this.center.z + this.zLen - ray.origin.z) * invZ
            true  -> (this.center.z - this.zLen - ray.origin.z) * invZ
        }

        return !((minT > maxTz) || (minTz > maxT))
    }

    override fun intersects(volume: BoundingVolume): Boolean {
        return volume.intersectsBox(this)
    }

    override fun intersectsBox(box: BoundingBox): Boolean {
        // The algorithm to model bounding box to bounding box intersection
        // uses interval intersection testing to determine if two bounding
        // boxes overlap (and thus intersect).
        //
        // The basic formula is that for each axis, test if:
        //  a(min) > b(max) or a(max) < b(min)
        // where either condition denotes a divergence of the two intervals
        // along that specific axis.
        //
        // Courtesy of MIT, "Intersection Testing".
        if(((this.center.x - this.xLen) > (box.center.x + box.xLen)) ||
           ((this.center.x + this.xLen) < (box.center.x - box.xLen))) {
            return false
        }
        else if(((this.center.y - this.yLen) > (box.center.y + box.yLen)) ||
                ((this.center.y + this.yLen) < (box.center.y - box.yLen))) {
            return false
        }

        return !(((this.center.z - this.zLen) > (box.center.z + box.zLen)) ||
                 ((this.center.z + this.zLen) < (box.center.z - box.zLen)))
    }

    override fun intersectsSphere(sphere: BoundingSphere): Boolean {
        // The algorithm to model bounding sphere to bounding box
        // intersections exploits simple distance computations to determine
        // whether or not they overlap.
        //
        // In brief, a sphere and a box intersect if the distance from the
        // center of the sphere to one of the extreme points on the box is
        // less than or equal to the radius of the sphere.
        //
        // Courtesy of Jim Arvo, "Graphics Gems 2".
        var dist = sphere.radius * sphere.radius
        val maxX = this.center.x + this.xLen
        val maxY = this.center.y + this.yLen
        val maxZ = this.center.z + this.zLen
        val minX = this.center.x - this.xLen
        val minY = this.center.y - this.yLen
        val minZ = this.center.z - this.zLen

        if(sphere.center.x < minX) {
            dist -= MathUtils.pow(sphere.center.x - minX, 2f)
        }
        else if(sphere.center.x > maxX) {
            dist -= MathUtils.pow(sphere.center.x - maxX, 2f)
        }

        if(sphere.center.y < minY) {
            dist -= MathUtils.pow(sphere.center.y - minY, 2f)
        }
        else if(sphere.center.y > maxY) {
            dist -= MathUtils.pow(sphere.center.y - maxY, 2f)
        }

        if(sphere.center.z < minZ) {
            dist -= MathUtils.pow(sphere.center.z - minZ, 2f)
        }
        else if(sphere.center.z > maxZ) {
            dist -= MathUtils.pow(sphere.center.z - maxZ, 2f)
        }

        return dist >= 0f
    }

    override fun toString(): String =
        "(<${this.xLen}, ${this.yLen}, ${this.zLen}>, ${this.center})"
}
