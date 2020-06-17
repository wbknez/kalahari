package com.willoutwest.kalahari.texture.patterns

import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.ComputeUtils
import com.willoutwest.kalahari.math.MathUtils
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.math.noise.Speaker
import com.willoutwest.kalahari.texture.Image
import com.willoutwest.kalahari.texture.Texture

/**
 * Represents an implementation of [Texture] that applies noise function to
 * an image to produce a tiled gradient.
 *
 * @property a
 *           The noise scaling to use.
 * @property image
 *           The image to use (as a ramp).
 */
class RampTexture(speaker: Speaker, val image: Image, val a: Float)
    : AbstractProceduralTexture(speaker), Cloneable, Texture {

    override fun getColor(record: Intersection, store: Color3): Color3 {
        val cache         = ComputeUtils.localCache
        val localPosition = cache.points.borrow()

        localPosition.set(record.localPosition)
            .transformSelf(this.invTransform)

        val output = this.speaker.output(localPosition)
        val y      = localPosition.y + this.a * output
        val u      = 0.5f * (1f + MathUtils.sin(y))
        val column = (u * (this.image.height - 1)).toInt()

        cache.points.reuse(localPosition)
        
        return store.set(this.image[column, 0])
    }
}