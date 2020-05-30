package com.willoutwest.kalahari.scene.surfaces

import com.willoutwest.kalahari.math.EpsilonTable
import com.willoutwest.kalahari.math.MathUtils
import com.willoutwest.kalahari.math.Normal3
import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.Ray3
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.scene.Surface
import com.willoutwest.kalahari.util.FloatContainer
import com.willoutwest.kalahari.util.ObjectContainer

/**
 * Represents an implementation of [Surface] that is defined as an implicit
 * axis-aligned box.
 *
 * @property center
 *           The center of the box.
 * @property xLen
 *           The extension length along the x-axis.
 * @property yLen
 *           The extension length along the y-axis.
 * @property zLen
 *           The extension length along the z-axis.
 */
data class Box(val center: Point3 = Point3.Zero.clone(),
               val xLen: Float = 1f,
               val yLen: Float = 1f,
               val zLen: Float = 1f) : Cloneable, Surface {

    companion object {

        /**
         * The floating-point bias to use to prevent incorrect results
         * during integer casting.
         */
        const val Bias      = 1.000001

        /**
         * Denotes a successful intersection with a normal, or "hit", ray.
         */
        const val HitEps    = 0.0001f

        /**
         * The unique identifier in an epsilon table.
         */
        const val ID        = "surf.box"

        /**
         * Denotes a successful intersection with a reflected, or "shadow",
         * ray.
         */
        const val ShadowEps = 0.001f
    }

    /**
     * Constructor.
     *
     * @param box
     *        The box to copy from.
     */
    constructor(box: Box?) : this(box!!.center, box.xLen, box.yLen, box.zLen)

    override fun clone(): Box = Box(this)

    private fun computeNormal(hit: Point3, store: Normal3) {
        val pX = hit.x - this.center.x
        val pY = hit.y - this.center.y
        val pZ = hit.z - this.center.z

        store.set(((pX / this.xLen) * Bias).toInt().toFloat(),
                  ((pY / this.yLen) * Bias).toInt().toFloat(),
                  ((pZ / this.zLen) * Bias).toInt().toFloat())
            .normalizeSelf()
    }

    override fun intersects(ray: Ray3, tMin: FloatContainer,
                            record: Intersection, eps: EpsilonTable): Boolean {
        val tx1 = (this.center.x - this.xLen - ray.origin.x) * ray.invDir.x
        val tx2 = (this.center.x + this.xLen - ray.origin.x) * ray.invDir.x

        var tmin = MathUtils.min(tx1, tx2)
        var tmax = MathUtils.max(tx1, tx2)

        val ty1 = (this.center.y - this.yLen - ray.origin.y) * ray.invDir.y
        val ty2 = (this.center.y + this.yLen - ray.origin.y) * ray.invDir.y

        tmin = MathUtils.max(tmin, MathUtils.min(ty1, ty2))
        tmax = MathUtils.min(tmax, MathUtils.max(ty1, ty2))

        val tz1 = (this.center.z - this.zLen - ray.origin.z) * ray.invDir.z
        val tz2 = (this.center.z + this.zLen - ray.origin.z) * ray.invDir.z

        tmin = MathUtils.max(tmin, MathUtils.min(tz1, tz2))
        tmax = MathUtils.min(tmax, MathUtils.max(tz1, tz2))

        return when(tmax < MathUtils.max(eps[ID], tmin)) {
            false -> {
                val t = when(tmin > eps[ID]) {
                    false -> tmax
                    true  -> tmin
                }

                this.computeNormal(record.localPosition, record.normal)

                ray.projectAlong(t, record.localPosition)
                ray.projectAlong(t, record.worldPosition)

                record.reversed = false

                tMin.value = t

                true
            }
            true  -> false
        }
    }

    override fun shadows(ray: Ray3, tMin: FloatContainer, obj: ObjectContainer,
                         eps: EpsilonTable, tMax: Float): Boolean {
        val tx1 = (this.center.x - this.xLen - ray.origin.x) * ray.invDir.x
        val tx2 = (this.center.x + this.xLen - ray.origin.x) * ray.invDir.x

        var tmin = MathUtils.min(tx1, tx2)
        var tmax = MathUtils.max(tx1, tx2)

        val ty1 = (this.center.y - this.yLen - ray.origin.y) * ray.invDir.y
        val ty2 = (this.center.y + this.yLen - ray.origin.y) * ray.invDir.y

        tmin = MathUtils.max(tmin, MathUtils.min(ty1, ty2))
        tmax = MathUtils.min(tmax, MathUtils.max(ty1, ty2))

        val tz1 = (this.center.z - this.zLen - ray.origin.z) * ray.invDir.z
        val tz2 = (this.center.z + this.zLen - ray.origin.z) * ray.invDir.z

        tmin = MathUtils.max(tmin, MathUtils.min(tz1, tz2))
        tmax = MathUtils.min(tmax, MathUtils.max(tz1, tz2))

        return when(tmax >= MathUtils.max(eps[ID], tmin) && tMax > tmax) {
            false -> false
            true  -> {
                tMin.value = when(tmin > eps[ID]) {
                    false -> tmax
                    true  -> tmin
                }

                true
            }
        }
    }
}