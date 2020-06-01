package com.willoutwest.kalahari.texture.mappers

import com.willoutwest.kalahari.math.MathUtils
import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.TexCoord2
import com.willoutwest.kalahari.texture.ImageMapper
import kotlin.math.atan2

/**
 * Represents an implementation of [ImageMapper] that maps an intersection
 * point on a geometric surface onto a cylinder in texture space.
 */
class CylindricalMapper : ImageMapper {

    override fun map(hit: Point3, width: Int, height: Int,
                     store: TexCoord2): TexCoord2 {
        val phi = atan2(hit.x, hit.z)

        store.u = when(phi < 0.0) {
            false -> phi / MathUtils.InvTwoPi
            true  -> 1 + (phi / MathUtils.InvTwoPi)
        }
        store.v = (hit.y + 1) * 0.5f

        return store
    }
}