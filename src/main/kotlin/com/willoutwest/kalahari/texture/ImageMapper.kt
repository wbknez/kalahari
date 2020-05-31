package com.willoutwest.kalahari.texture

import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.TexCoord2

/**
 * Represents a mechanism for mapping the intersection point of a geometric
 * object in three-dimensional Cartesian space to a two-dimensional texture
 * space.
 */
interface ImageMapper {

    /**
     * Maps the specified hit point to a texture space with the specified
     * width and height.
     *
     * @param hit
     *        The intersection point to use.
     * @param width
     *        The width in pixels.
     * @param height
     *        The height in pixels.
     * @param store
     *        The texture coordinates to store the result in.
     * @return A reference to [store] for easy chaining.
     */
    fun map(hit: Point3, width: Int, height: Int, store: TexCoord2): TexCoord2
}