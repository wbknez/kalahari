package com.willoutwest.kalahari.texture.mappers

import com.willoutwest.kalahari.math.MathUtils
import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.TexCoord2
import com.willoutwest.kalahari.texture.ImageMapper
import kotlin.math.acos
import kotlin.math.atan2

/**
 * Represents an implementation of [ImageMapper] that maps an intersection
 * point on a geometric surface onto a sphere in texture space.
 */
class SphericalMapper : ImageMapper {

    override fun map(hit: Point3, width: Int, height: Int, store: TexCoord2)
        : TexCoord2 {

        val phi   = atan2(hit.x, hit.z)
        val theta = acos(hit.y)

        store.u = when(phi < 0.0) {
            false -> phi * MathUtils.InvTwoPi
            true  -> 1f + (phi * MathUtils.InvTwoPi)
        }
        store.v = 1f - theta * MathUtils.InvPi

        return store
    }
}