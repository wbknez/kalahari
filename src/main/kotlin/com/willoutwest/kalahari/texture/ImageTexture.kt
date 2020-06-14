package com.willoutwest.kalahari.texture

import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.ComputeUtils
import com.willoutwest.kalahari.math.intersect.Intersection

/**
 * Represents an implementation of [Texture] that maps and displays an image
 * onto an arbitrary geometric object.
 *
 * @property image
 *           The image data.
 * @property mapper
 *           The mechanism to compute texture coordinates.
 */
data class ImageTexture(val image: Image, val mapper: ImageMapper)
    : AbstractTexture(), Cloneable, Texture {

    constructor(tex: ImageTexture?) : this(tex!!.image, tex.mapper)

    public override fun clone(): ImageTexture = ImageTexture(this)

    override fun getColor(record: Intersection, store: Color3): Color3 {
        val cache         = ComputeUtils.localCache

        val localPosition = cache.points.borrow()
        val uv            = cache.uvs.borrow()

        localPosition.set(record.localPosition)
            .transformSelf(this.invTransform)
        
        this.mapper.map(localPosition, image.width, image.height, uv)

        val col = (uv.u * (image.width - 1)).toInt()
        val row = (uv.v * (image.height - 1)).toInt()

        cache.points.reuse(localPosition)
        cache.uvs.reuse(uv)

        return store.set(this.image[col, row])
    }
}