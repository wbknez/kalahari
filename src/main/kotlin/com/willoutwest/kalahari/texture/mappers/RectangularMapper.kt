package com.willoutwest.kalahari.texture.mappers

import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.TexCoord2
import com.willoutwest.kalahari.texture.ImageMapper

/**
 * Represents an implementation of [ImageMapper] that maps an intersection
 * point on a geometric surface onto a rectangle in texture space.
 */
class RectangularMapper : ImageMapper {

    override fun map(hit: Point3, width: Int, height: Int,
                     store: TexCoord2): TexCoord2 =
        store.set((hit.z + 1f) * 0.5f, (hit.x + 1f) * 0.5f)
}